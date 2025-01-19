package com.simsimbookstore.frontserver.security.handler;

import com.simsimbookstore.frontserver.security.userDetails.CustomUserDetails;
import com.simsimbookstore.frontserver.token.dto.JwtGenerateRequestDto;
import com.simsimbookstore.frontserver.token.mapper.JwtMapper;
import com.simsimbookstore.frontserver.token.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class CustomOAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final TokenService loginSuccessHandlerService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        super.onAuthenticationSuccess(request, response, chain, authentication);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        JwtGenerateRequestDto jwtGenerateRequestDto = JwtMapper.toJwtGenerateRequestDto(customUserDetails);

        // 토큰 발급
        loginSuccessHandlerService.createJwtCookie(jwtGenerateRequestDto,response);

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
