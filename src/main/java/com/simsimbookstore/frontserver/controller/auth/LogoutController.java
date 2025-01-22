package com.simsimbookstore.frontserver.controller.auth;

import com.simsimbookstore.frontserver.cart.service.CartService;
import com.simsimbookstore.frontserver.security.userDetails.CustomUserDetails;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Objects;


@RequiredArgsConstructor
@RequestMapping("/logout")
@Controller
public class LogoutController {
    private final CartService cartService;


    @PostMapping
    public String logout(
            @AuthenticationPrincipal CustomUserDetails user,
            HttpServletResponse response
            ) {

        if (Objects.nonNull(user)) {
            cartService.migrateCartToDB(String.valueOf(user.getUserId()));
        }

        Cookie accessToken = new Cookie("accessToken", null);
        accessToken.setMaxAge(0);
        accessToken.setPath("/");
        response.addCookie(accessToken);

        Cookie refreshToken = new Cookie("refreshToken", null);
        refreshToken.setMaxAge(0);
        refreshToken.setPath("/");
        response.addCookie(refreshToken);

        return "redirect:/";
    }
}
