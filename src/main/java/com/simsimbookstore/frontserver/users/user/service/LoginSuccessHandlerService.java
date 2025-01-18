package com.simsimbookstore.frontserver.users.user.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simsimbookstore.frontserver.security.userDetails.CustomUserDetails;
import com.simsimbookstore.frontserver.users.user.dto.JwtGenerateRequestDto;
import com.simsimbookstore.frontserver.users.user.dto.UserLateLoginDateUpdateRequestDto;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;

@RequiredArgsConstructor
@Service
public class LoginSuccessHandlerService {
    private final UserService userService;
    private final ObjectMapper objectMapper;

    public void loginProcess(JwtGenerateRequestDto jwtGenerateRequestDto, HttpServletResponse response) throws IOException {
        // JWT 생성 및 응답 설정
        String jsonResponse = userService.generateJwt(jwtGenerateRequestDto);
        HashMap<String, String> tokens = objectMapper.readValue(jsonResponse, HashMap.class);
        addCookieToResponse("accessToken", tokens.get("accessToken"), 3600, response); // 1시간
        addCookieToResponse("refreshToken", tokens.get("refreshToken"), 7 * 24 * 3600, response); // 7일

        // 마지막 로그인 시간 업데이트
        userService.updateUserLatestLoginDate(jwtGenerateRequestDto.getUserId(), UserLateLoginDateUpdateRequestDto.builder()
                .latestLoginDate(LocalDateTime.now())
                .build());
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