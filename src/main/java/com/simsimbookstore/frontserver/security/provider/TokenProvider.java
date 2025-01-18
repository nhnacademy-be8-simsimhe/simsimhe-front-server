package com.simsimbookstore.frontserver.security.provider;

import com.simsimbookstore.frontserver.security.userDetails.CustomUserDetails;
import com.simsimbookstore.frontserver.users.localUser.service.LocalUserService;
import com.simsimbookstore.frontserver.users.role.dto.RoleName;
import com.simsimbookstore.frontserver.users.socialUser.service.SocialUserService;
import com.simsimbookstore.frontserver.users.user.dto.UserResponse;
import com.simsimbookstore.frontserver.users.user.service.CustomOauth2UserService;
import com.simsimbookstore.frontserver.users.user.service.CustomUserDetailsService;
import com.simsimbookstore.frontserver.users.user.service.UserService;
import com.simsimbookstore.frontserver.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.io.Decoders;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class TokenProvider {
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;
    private final UserService userService;

    public boolean validateToken(String token){
        try{
            jwtUtil.parse(token);
            return true;
        } catch (Exception e){
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
}
