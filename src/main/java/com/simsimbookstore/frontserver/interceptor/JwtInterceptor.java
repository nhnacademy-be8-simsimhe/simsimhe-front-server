package com.simsimbookstore.frontserver.interceptor;

import com.simsimbookstore.frontserver.util.CookieUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;
import java.util.Optional;


@RequiredArgsConstructor
public class JwtInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        Optional<Cookie> accessToken = CookieUtils.getCookie(request, "accessToken");
//
//        if (accessToken.isPresent()) {
//            requestTemplate.header("Authorization", "Bearer " + accessToken.get().getValue());
//        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object credentials = authentication.getCredentials();
            if (credentials != null) {
                requestTemplate.header("Authorization", "Bearer " + credentials.toString());
            }
        }
    }
}
