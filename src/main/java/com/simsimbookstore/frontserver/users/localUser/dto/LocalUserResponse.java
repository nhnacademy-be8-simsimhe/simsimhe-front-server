package com.simsimbookstore.frontserver.users.localUser.dto;

import com.simsimbookstore.frontserver.users.role.dto.RoleName;
import lombok.*;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class LocalUserResponse {
    private Long userId;

    private List<RoleName> roles;

    private String loginId;

    private String password;
}
