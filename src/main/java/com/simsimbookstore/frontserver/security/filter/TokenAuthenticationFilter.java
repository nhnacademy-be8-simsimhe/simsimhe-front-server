package com.simsimbookstore.frontserver.security.filter;

import com.simsimbookstore.frontserver.security.handler.CustomAuthFailureHandler;
import com.simsimbookstore.frontserver.security.provider.TokenProvider;
import com.simsimbookstore.frontserver.users.user.dto.JwtGenerateRequestDto;
import com.simsimbookstore.frontserver.users.user.service.TokenService;
import com.simsimbookstore.frontserver.util.CookieUtils;
import com.simsimbookstore.frontserver.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final TokenProvider tokenProvider;
    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Optional<Cookie> optionalAccessToken = CookieUtils.getCookie(request, "accessToken");

        if (optionalAccessToken.isPresent()) {
            String accessToken = optionalAccessToken.get().getValue();
            try{
                jwtUtil.parse(accessToken);
                setAuthentication(accessToken);
            } catch (ExpiredJwtException accessTokenExpiredException) {

                // 리프레쉬토큰으로 재발급
                Optional<Cookie> optionalRefreshToken = CookieUtils.getCookie(request, "refreshToken");
                if (optionalRefreshToken.isPresent()) {
                    String refreshToken = optionalRefreshToken.get().getValue();

                    Claims claims = null;
                    try{
                        claims = jwtUtil.parse(refreshToken);
                    } catch (Exception e) {
                        filterChain.doFilter(request, response);
                        return;
                    }

                    Long userId = claims.get("userId", Long.class);
                    Boolean isSocial = claims.get("isSocial", Boolean.class);

                    JwtGenerateRequestDto jwtGenerateRequestDto = JwtGenerateRequestDto.builder()
                            .subject(claims.getSubject())
                            .userId(userId)
                            .isSocial(isSocial)
                            .build();
                    tokenService.createJwtCookie(jwtGenerateRequestDto,response);
                    Optional<Cookie> optionalNewToken = CookieUtils.getCookie(request, "refreshToken");
                    if (optionalNewToken.isPresent()) {
                        String newToken = optionalNewToken.get().getValue();
                        setAuthentication(newToken);
                    }
                }
            } catch (Exception e){
                filterChain.doFilter(request, response);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private void setAuthentication(String token) {
        Authentication authentication = tokenProvider.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
