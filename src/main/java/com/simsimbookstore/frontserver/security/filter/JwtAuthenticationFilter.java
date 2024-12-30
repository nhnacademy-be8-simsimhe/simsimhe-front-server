package com.simsimbookstore.frontserver.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simsimbookstore.frontserver.security.userDetails.CustomUserDetails;
import com.simsimbookstore.frontserver.user.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username,password);
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        return authentication;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        // 로그인 성공 시 jwt 발급 요청
        CustomUserDetails customUserDetails = (CustomUserDetails) authResult.getPrincipal();

        String jsonResponse = userService.generateJwt(customUserDetails.getUsername());
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String,String> tokens = objectMapper.readValue(jsonResponse, HashMap.class);
        String accessToken = tokens.get("accessToken");
        String refreshToken = tokens.get("refreshToken");


        Cookie accessTokenCookie = new Cookie("accessToken",accessToken);
        accessTokenCookie.setMaxAge(3600); //1시간
        response.addCookie(accessTokenCookie);

        Cookie refreshTokenCookie = new Cookie("refreshToken",refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setMaxAge(7 * 24 * 3600); //7일
        response.addCookie(refreshTokenCookie);

        super.successfulAuthentication(request, response, chain, authResult);
    }
}
