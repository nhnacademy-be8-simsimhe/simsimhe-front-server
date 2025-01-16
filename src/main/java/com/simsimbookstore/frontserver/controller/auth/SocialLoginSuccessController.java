package com.simsimbookstore.frontserver.controller.auth;

import com.simsimbookstore.frontserver.security.userDetails.CustomUserDetails;
import com.simsimbookstore.frontserver.users.user.dto.JwtGenerateRequestDto;
import com.simsimbookstore.frontserver.users.user.service.LoginSuccessHandlerService;
import com.simsimbookstore.frontserver.util.CookieUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Map;

@RequestMapping("/users")
@RequiredArgsConstructor
@Controller
public class SocialLoginSuccessController {
    private final LoginSuccessHandlerService loginSuccessHandlerService;

    @GetMapping("/socialLogin/success")
    public String socialLoginSuccess(
            @RequestParam String username,
            @RequestParam String userId,
            @RequestParam String isSocial,
            HttpServletResponse response
    ) {
        JwtGenerateRequestDto jwtGenerateRequestDto = JwtGenerateRequestDto.builder()
                .subject(username)
                .userId(Long.parseLong(userId))
                .isSocial(Boolean.parseBoolean(isSocial))
                .build();
        try {
            loginSuccessHandlerService.loginProcess(jwtGenerateRequestDto, response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/";
    }
}
