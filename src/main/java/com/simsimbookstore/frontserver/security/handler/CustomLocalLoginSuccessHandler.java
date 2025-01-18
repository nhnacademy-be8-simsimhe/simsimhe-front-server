package com.simsimbookstore.frontserver.security.handler;

import com.simsimbookstore.frontserver.security.userDetails.CustomUserDetails;
import com.simsimbookstore.frontserver.users.user.dto.JwtGenerateRequestDto;
import com.simsimbookstore.frontserver.users.user.service.LoginSuccessHandlerService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;


@RequiredArgsConstructor
public class CustomLocalLoginSuccessHandler implements AuthenticationSuccessHandler {
    private final LoginSuccessHandlerService loginSuccessHandlerService;



    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        AuthenticationSuccessHandler.super.onAuthenticationSuccess(request, response, chain, authentication);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 토큰 발급
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        JwtGenerateRequestDto jwtGenerateRequestDto = JwtGenerateRequestDto.builder()
                .subject(customUserDetails.getUsername())
                .userId(customUserDetails.getUserId())
                .isSocial(customUserDetails.isSocial())
                .build();

        loginSuccessHandlerService.loginProcess(jwtGenerateRequestDto, response);

//        String jsonResponse = userService.generateJwt(customUserDetails.getUserId());
//        ObjectMapper objectMapper = new ObjectMapper();
//        HashMap<String,String> tokens = objectMapper.readValue(jsonResponse, HashMap.class);
//        String accessToken = tokens.get("accessToken");
//        String refreshToken = tokens.get("refreshToken");
//
//
//        Cookie accessTokenCookie = new Cookie("accessToken",accessToken);
//        accessTokenCookie.setMaxAge(3600); //1시간
//        response.addCookie(accessTokenCookie);
//
//        Cookie refreshTokenCookie = new Cookie("refreshToken",refreshToken);
//        refreshTokenCookie.setHttpOnly(true);
//        refreshTokenCookie.setMaxAge(7 * 24 * 3600); //7일
//        response.addCookie(refreshTokenCookie);
//
//        // 마지막 로그인 시간 업데이트
//        UserLateLoginDateUpdateRequestDto requestDto = UserLateLoginDateUpdateRequestDto.builder()
//                .latestLoginDate(LocalDateTime.now())
//                .build();
//        userService.updateUserLatestLoginDate(customUserDetails.getUserId(), requestDto);
    }
}
