package com.simsimbookstore.frontserver.security.provider;

import com.simsimbookstore.frontserver.security.userDetails.CustomUserDetails;
import com.simsimbookstore.frontserver.users.role.dto.RoleName;
import com.simsimbookstore.frontserver.users.user.dto.JwtGenerateRequestDto;
import com.simsimbookstore.frontserver.users.user.dto.UserResponse;
import com.simsimbookstore.frontserver.users.user.feign.JwtServiceClient;
import com.simsimbookstore.frontserver.users.user.service.CustomUserDetailsService;
import com.simsimbookstore.frontserver.users.user.service.TokenService;
import com.simsimbookstore.frontserver.users.user.service.UserService;
import com.simsimbookstore.frontserver.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

import java.util.ArrayList;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TokenProvider {
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final JwtServiceClient jwtServiceClient;

    public boolean validateToken(String token){
        try{
            Claims claims = jwtUtil.parse(token);
            return true;
        } catch (ExpiredJwtException e){
            throw e;
        } catch (JwtException | IllegalArgumentException e){
            return false;
        }
    }

    public Authentication getAuthentication(String token){
        Claims claims = jwtUtil.parse(token);
        Long userId = claims.get("userId", Long.class);
        Boolean isSocial = claims.get("isSocial", Boolean.class);
        UserResponse userResponse = userService.findUserByUserId(userId);

        CustomUserDetails customUserDetails = CustomUserDetails.builder()
                .userId(userResponse.getUserId())
                .principalName(claims.getSubject())
                .authorities(new ArrayList<>())
                .latestLoginDate(userResponse.getLatestLoginDate())
                .isSocial(isSocial)
                .build();

        for (RoleName role : userResponse.getRoles()){
            customUserDetails.addRole(role);
        }
        return new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
    }

    public Optional<String> generateTokenByRefreshToken(String refreshToken){
        Claims claims = jwtUtil.parse(refreshToken);
        Long userId = claims.get("userId", Long.class);
        Boolean isSocial = claims.get("isSocial", Boolean.class);

        JwtGenerateRequestDto jwtGenerateRequestDto = JwtGenerateRequestDto.builder()
                .subject(claims.getSubject())
                .userId(userId)
                .isSocial(isSocial)
                .build();

        Optional<String> optionalNewToken = Optional.of(jwtServiceClient.generateJwt(jwtGenerateRequestDto));
        return optionalNewToken;
    }
}
