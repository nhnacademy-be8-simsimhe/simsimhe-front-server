package com.simsimbookstore.frontserver.token.dto;

import com.simsimbookstore.frontserver.users.role.dto.RoleName;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Setter
@Getter
public class JwtGenerateRequestDto {
    private Long userId;
    private String subject;
    private boolean isSocial;
    Set<RoleName> roles = new HashSet<>();

    public void addRole(RoleName role) {
        if (Objects.isNull(roles)) {
            roles = new HashSet<>();
        }
        roles.add(role);
    }
}
