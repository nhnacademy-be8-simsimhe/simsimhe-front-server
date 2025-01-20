package com.simsimbookstore.frontserver.security.filter;

import com.simsimbookstore.frontserver.token.mapper.JwtMapper;
import com.simsimbookstore.frontserver.token.provider.TokenProvider;
import com.simsimbookstore.frontserver.token.dto.JwtGenerateRequestDto;
import com.simsimbookstore.frontserver.token.service.TokenService;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Slf4j
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
            try {
                jwtUtil.parse(accessToken);
                setAuthentication(accessToken);
                response.addHeader("Authorization", "Bearer " + accessToken);
            } catch (ExpiredJwtException accessTokenExpiredException) {
                // 리프레쉬토큰으로 재발급
                Optional<Cookie> optionalRefreshToken = CookieUtils.getCookie(request, "refreshToken");
                if (optionalRefreshToken.isPresent()) {
                    String refreshToken = optionalRefreshToken.get().getValue();
                    try {
                        refreshTokenAuthenticate(refreshToken,response);
//                        response.sendRedirect(request.getRequestURI());
//                        return;
                    } catch (Exception e) {
                        log.error(e.getMessage());
                        filterChain.doFilter(request, response);
                        return;
                    }
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                filterChain.doFilter(request, response);
                return;
            }
        }
        else{
            try{
                Optional<Cookie> optionalRefreshToken = CookieUtils.getCookie(request, "refreshToken");
                if (optionalRefreshToken.isPresent()) {
                    String refreshToken = optionalRefreshToken.get().getValue();
                    try {
                        refreshTokenAuthenticate(refreshToken,response);
//                        response.sendRedirect(request.getRequestURI());
//                        return;
                    } catch (Exception e) {
                        filterChain.doFilter(request, response);
                        return;
                    }
                }
            } catch (Exception e){
                log.error(e.getMessage());
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

    private void refreshTokenAuthenticate(String refreshToken, HttpServletResponse response) throws Exception {
        Claims claims = jwtUtil.parse(refreshToken);
        JwtGenerateRequestDto jwtGenerateRequestDto = JwtMapper.toJwtGenerateRequestDto(claims);

        // 토큰 쿠키 생성
        Map<String, String> tokenMap = tokenService.createJwtCookie(jwtGenerateRequestDto, response);
        String newAccessToken = tokenMap.get("accessToken");
        response.addHeader("Authorization", "Bearer " + newAccessToken);
        setAuthentication(newAccessToken);
    }
}
