package com.simsimbookstore.frontserver.security.filter;


import com.simsimbookstore.frontserver.util.JsonUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class UserAuthenticationFilter extends OncePerRequestFilter {

    private final String HEADER = "Authorization";
    private final String PREFIX = "Bearer ";

    private final JsonUtil jsonUtil;

    public UserAuthenticationFilter(JsonUtil jsonUtil) {
        this.jsonUtil = jsonUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        Cookie[] cookies = request.getCookies();
//
//        // 쿠키에 rememberMe 값이 있다면 자동 로그인
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                if (cookie.getName().equals("rememberMe")) {
//                    String token = cookie.getValue();
//                    Claims claims = jsonUtil.parse(token);
//                    String username = claims.getSubject();
//
//                    List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
//                    Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
//                    SecurityContextHolder.getContext().setAuthentication(authentication);
//                }
//            }
//        }


        filterChain.doFilter(request, response);



//        String header = request.getHeader(HEADER);
//        if (Objects.isNull(header) || !header.startsWith(PREFIX)) {
//            filterChain.doFilter(request, response); // 토큰이 없는 경우 계속 진행, 만약 인증이 필요한 페이징에 경우 로근 페이지로 넘어감
//            return;
//        }
//
//        String token = header.replace(PREFIX, "");
//        Claims claims = jsonUtil.parse(token);
//        String username = claims.getSubject();
//
//        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
//        Authentication authentication = new UsernamePasswordAuthenticationToken(username,null,authorities);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        filterChain.doFilter(request, response);
    }
}
