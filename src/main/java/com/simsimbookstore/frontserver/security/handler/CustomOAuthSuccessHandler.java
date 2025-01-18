package com.simsimbookstore.frontserver.security.handler;

import com.simsimbookstore.frontserver.security.userDetails.CustomUserDetails;
import com.simsimbookstore.frontserver.users.user.dto.JwtGenerateRequestDto;
import com.simsimbookstore.frontserver.users.user.service.LoginSuccessHandlerService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class CustomOAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final LoginSuccessHandlerService loginSuccessHandlerService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        super.onAuthenticationSuccess(request, response, chain, authentication);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
//
//        Map<String,Object> paramMap = new HashMap<>();
//        paramMap.put("username", userDetails.getUsername());
//        paramMap.put("userId", userDetails.getUserId());
//        paramMap.put("isSocial", userDetails.isSocial());
//
//        String baseUrl = getBaseUrl(request);
//        UriComponentsBuilder redirectUrlBuilder = UriComponentsBuilder.fromUriString("http://localhost:" + request.getServerPort() + "/users/socialLogin/success")
//                .queryParam("username", userDetails.getUsername())
//                .queryParam("userId", userDetails.getUserId())
//                .queryParam("isSocial", userDetails.isSocial());
//        getRedirectStrategy().sendRedirect(request, response, redirectUrlBuilder.toUriString());
//
        JwtGenerateRequestDto jwtGenerateRequestDto = JwtGenerateRequestDto.builder()
                .subject(userDetails.getUsername())
                .userId(userDetails.getUserId())
                .isSocial(userDetails.isSocial())
                .build();

        loginSuccessHandlerService.loginProcess(jwtGenerateRequestDto,response);

        super.onAuthenticationSuccess(request, response, authentication);
    }

    private String getBaseUrl(HttpServletRequest request) {
        String scheme = request.getScheme(); // http 또는 https
        String serverName = request.getServerName(); // 도메인 이름 또는 IP
        int serverPort = request.getServerPort(); // 포트 번호
        String contextPath = request.getContextPath(); // 컨텍스트 경로

        StringBuilder baseUrl = new StringBuilder();
        baseUrl.append(scheme).append("://").append(serverName);

        // 기본 포트가 아닌 경우 포트 추가
        if ((scheme.equals("http") && serverPort != 80) || (scheme.equals("https") && serverPort != 443)) {
            baseUrl.append(":").append(serverPort);
        }

        baseUrl.append(contextPath);
        return baseUrl.toString();
    }



    //    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

//    }
}
