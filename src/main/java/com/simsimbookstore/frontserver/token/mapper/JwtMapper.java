package com.simsimbookstore.frontserver.token.mapper;

import com.simsimbookstore.frontserver.security.userDetails.CustomUserDetails;
import com.simsimbookstore.frontserver.token.dto.JwtGenerateRequestDto;
import com.simsimbookstore.frontserver.users.role.dto.RoleName;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public class JwtMapper {

    public static JwtGenerateRequestDto toJwtGenerateRequestDto(Claims claims) {
        Long userId = claims.get("userId", Long.class);
        Boolean isSocial = claims.get("isSocial", Boolean.class);
        List<String> rolesStr = (List<String>) claims.get("roles");
        List<RoleName> roles = rolesStr.stream().map(RoleName::valueOf).toList();

        JwtGenerateRequestDto jwtGenerateRequestDto = JwtGenerateRequestDto.builder()
                .subject(claims.getSubject())
                .userId(userId)
                .isSocial(isSocial)
                .build();

        for (RoleName role : roles) {
            jwtGenerateRequestDto.addRole(role);
        }

        return jwtGenerateRequestDto;
    }

    public static JwtGenerateRequestDto toJwtGenerateRequestDto(CustomUserDetails customUserDetails) {
        JwtGenerateRequestDto jwtGenerateRequestDto = JwtGenerateRequestDto.builder()
                .subject(customUserDetails.getUsername())
                .userId(customUserDetails.getUserId())
                .isSocial(customUserDetails.isSocial())
                .build();

        for (GrantedAuthority role : customUserDetails.getAuthorities()){
            String roleNameStr = role.toString().replace("ROLE_", "");
            RoleName roleName = RoleName.valueOf(roleNameStr);
            jwtGenerateRequestDto.addRole(roleName);
        }
        return jwtGenerateRequestDto;
    }
}
