package com.simsimbookstore.frontserver.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simsimbookstore.frontserver.security.userDetails.CustomUserDetails;
import com.simsimbookstore.frontserver.users.user.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.HashMap;

@RequiredArgsConstructor
public class LocalLoginSuccessHandler implements AuthenticationSuccessHandler {
    private final UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        AuthenticationSuccessHandler.super.onAuthenticationSuccess(request, response, chain, authentication);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

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
    }
}
