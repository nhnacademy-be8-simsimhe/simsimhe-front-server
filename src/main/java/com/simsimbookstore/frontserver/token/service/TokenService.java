package com.simsimbookstore.frontserver.token.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.simsimbookstore.frontserver.token.dto.JwtGenerateRequestDto;
import com.simsimbookstore.frontserver.token.feign.JwtServiceClient;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class TokenService {
    private final ObjectMapper objectMapper;
    private final JwtServiceClient jwtServiceClient;

    public String generateJwt(JwtGenerateRequestDto requestDto) {
        return jwtServiceClient.generateJwt(requestDto);
    }

    public Map<String,String> createJwtCookie(JwtGenerateRequestDto jwtGenerateRequestDto, HttpServletResponse response){
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

        Map<String,String> tokenMap = new HashMap<>();
        tokenMap.put("accessToken", tokens.get("accessToken"));
        tokenMap.put("refreshToken", tokens.get("refreshToken"));
        return tokenMap;
    }

    private void addCookieToResponse(String name, String value, int maxAge, HttpServletResponse response) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(maxAge);
        cookie.setPath("/");
        cookie.setSecure(false);
        response.addCookie(cookie);
    }
}