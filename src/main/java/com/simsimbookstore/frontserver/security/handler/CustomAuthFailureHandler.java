package com.simsimbookstore.frontserver.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simsimbookstore.frontserver.users.user.exception.CustomAccountExpiredException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;

@Component
public class CustomAuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        HashMap<String,Object> errorMap = new HashMap<>();
        String errorMessage;
        if (exception instanceof BadCredentialsException) {
            errorMessage = "아이디 또는 비밀번호가 옳바르지 않습니다.";
        }else if (exception instanceof UsernameNotFoundException){
            errorMessage = "아이디가 존재하지 않습니다.";
        }else if (exception instanceof DisabledException){
            errorMessage = "탈퇴한 유저 입니다.";
        }else if (exception instanceof CustomAccountExpiredException){
            CustomAccountExpiredException customException = (CustomAccountExpiredException) exception;
            errorMessage = "휴면 유저 입니다.";
            errorMap.put("userId",customException.getUserId());
        }

        else if (exception instanceof InternalAuthenticationServiceException){
            errorMessage = "내부적으로 발생한 시스템 문제로 요청을 처리할 수 없습니다. 관리자에게 문의하세요.";
        }
        else{
            errorMessage = "알 수 없는 이유로 로그인에 실패하였습니다 관리자에게 문의하세요.";
        }

        ObjectMapper mapper = new ObjectMapper();
        errorMap.put("error", errorMessage);
        errorMap.put("code", exception.getClass().getSimpleName());
        String jsonMap = mapper.writeValueAsString(errorMap);

        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write(jsonMap);
    }
}
