package com.simsimbookstore.frontserver.security.handler;

import com.simsimbookstore.frontserver.cart.service.CartService;
import com.simsimbookstore.frontserver.security.userDetails.CustomUserDetails;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {
    private final CartService cartService;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Cookie accessToken = new Cookie("accessToken", null);
        accessToken.setMaxAge(0);
        accessToken.setPath("/");
        response.addCookie(accessToken);

        Cookie refreshToken = new Cookie("refreshToken", null);
        refreshToken.setMaxAge(0);
        refreshToken.setPath("/");
        response.addCookie(refreshToken);


//        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
//        cartService.migrateCartToDB(String.valueOf(customUserDetails.getUserId()));
    }
}
