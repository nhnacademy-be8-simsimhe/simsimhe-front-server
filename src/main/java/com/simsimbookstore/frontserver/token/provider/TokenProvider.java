package com.simsimbookstore.frontserver.token.provider;

import com.simsimbookstore.frontserver.security.userDetails.CustomUserDetails;
import com.simsimbookstore.frontserver.users.role.dto.RoleName;
import com.simsimbookstore.frontserver.users.user.service.UserService;
import com.simsimbookstore.frontserver.util.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TokenProvider {
    private final JwtUtil jwtUtil;
    private final UserService userService;
//    private final JwtServiceClient jwtServiceClient;

//    public boolean validateToken(String token){
//        try{
//            Claims claims = jwtUtil.parse(token);
//            return true;
//        } catch (ExpiredJwtException e){
//            throw e;
//        } catch (JwtException | IllegalArgumentException e){
//            return false;
//        }
//    }

    public Authentication getAuthentication(String token){
        Claims claims = jwtUtil.parse(token);
        Long userId = claims.get("userId", Long.class);
        Boolean isSocial = claims.get("isSocial", Boolean.class);
        List<String> rolesStr = (List<String>) claims.get("roles");

        List<RoleName> roles = rolesStr.stream().map(RoleName::valueOf).toList();

        CustomUserDetails customUserDetails = CustomUserDetails.builder()
                .userId(userId)
                .principalName(claims.getSubject())
                .authorities(new ArrayList<>())
                .isSocial(isSocial)
                .build();

        for (RoleName role : roles){
            customUserDetails.addRole(RoleName.valueOf(role.name()));
        }
        return new UsernamePasswordAuthenticationToken(customUserDetails, token, customUserDetails.getAuthorities());
    }
}
