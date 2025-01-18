package com.simsimbookstore.frontserver.users.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.simsimbookstore.frontserver.users.user.dto.JwtGenerateRequestDto;
import com.simsimbookstore.frontserver.users.user.dto.UserLateLoginDateUpdateRequestDto;
import com.simsimbookstore.frontserver.users.user.feign.JwtServiceClient;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;

@RequiredArgsConstructor
@Service
public class TokenService {
    private final ObjectMapper objectMapper;
    private final JwtServiceClient jwtServiceClient;

    public String generateJwt(JwtGenerateRequestDto requestDto) {
        return jwtServiceClient.generateJwt(requestDto);
    }

    public void createJwtCookie(JwtGenerateRequestDto jwtGenerateRequestDto, HttpServletResponse response){
        // JWT 생성 및 응답 설정
        String jsonResponse = jwtServiceClient.generateJwt(jwtGenerateRequestDto);
        HashMap<String, String> tokens = null;
        try {
            tokens = objectMapper.readValue(jsonResponse, HashMap.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        addCookieToResponse("accessToken", tokens.get("accessToken"), 3600, response); // 1시간
        addCookieToResponse("refreshToken", tokens.get("refreshToken"), 7 * 24 * 3600, response); // 7일
    }

    private void addCookieToResponse(String name, String value, int maxAge, HttpServletResponse response) {
        Cookie cookie = new Cookie(name, value);
        cookie.setDomain("localhost");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(maxAge);
        cookie.setPath("/");
        cookie.setSecure(false);
        response.addCookie(cookie);
    }
}