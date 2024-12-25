package com.simsimbookstore.frontserver.user.controller;

import com.simsimbookstore.frontserver.user.service.SocialService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Slf4j
@Controller()
@RequestMapping("/auth")
public class AuthController {
    private final SocialService socialService;

    public AuthController(SocialService socialService) {
        this.socialService = socialService;
    }

    @GetMapping("/paycoLogin")
    public ResponseEntity<String> authorize() {
        String url = socialService.getPaycoUrl();

        String redirectUrl = "/oauth2/authorization/payco";
        return ResponseEntity.status(HttpStatus.FOUND).header("Location", redirectUrl).build();
//        return ResponseEntity
//                .status(HttpStatus.FOUND)
//                .header("Location", url).build();
    }

    @GetMapping("/paycoLogin/callback")
    public String paycoCallback(
            HttpSession session,
            @RequestParam(value = "code") String code
            ) {

        String accessToken = socialService.getAccessToken(code);
        session.setAttribute("token", accessToken);
        String result = socialService.getUserInfo(accessToken);

        return "paycoCallback";
    }

    @GetMapping("/paycoLogout")
    public String paycoLogout(HttpSession session) {
        String token = (String) session.getAttribute("token");
        String result = "";
        try {
            String encodedToken = URLEncoder.encode(token,"UTF-8");
            result = socialService.logoutPayco(encodedToken);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/";
    }
}
