package com.simsimbookstore.frontserver.security.handler;

import com.simsimbookstore.frontserver.security.userDetails.CustomUserDetails;
import com.simsimbookstore.frontserver.token.dto.JwtGenerateRequestDto;
import com.simsimbookstore.frontserver.token.mapper.JwtMapper;
import com.simsimbookstore.frontserver.users.user.dto.UserLateLoginDateUpdateRequestDto;
import com.simsimbookstore.frontserver.token.service.TokenService;
import com.simsimbookstore.frontserver.users.user.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.time.LocalDateTime;


@RequiredArgsConstructor
public class CustomLocalLoginSuccessHandler implements AuthenticationSuccessHandler {
    private final TokenService tokenService;
    private final UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        AuthenticationSuccessHandler.super.onAuthenticationSuccess(request, response, chain, authentication);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        JwtGenerateRequestDto jwtGenerateRequestDto = JwtMapper.toJwtGenerateRequestDto(customUserDetails);

        // 토큰 발급
        tokenService.createJwtCookie(jwtGenerateRequestDto, response);

        // 마지막 로그인 시간 업데이트
        UserLateLoginDateUpdateRequestDto requestDto = UserLateLoginDateUpdateRequestDto.builder()
                .latestLoginDate(LocalDateTime.now())
                .build();

        userService.updateUserLatestLoginDate(jwtGenerateRequestDto.getUserId(), requestDto);
    }
}
