package com.simsimbookstore.frontserver.users.socialUser.dto;

import com.simsimbookstore.frontserver.users.role.dto.RoleName;
import com.simsimbookstore.frontserver.users.user.dto.UserStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class SocialUserResponse {
    private Long userId;

    private List<RoleName> roles;

    private String loginId;

    private UserStatus userStatus;

    private LocalDateTime latestLoginDate;

    private String oauthId;

    private Provider provider;
}
