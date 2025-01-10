package com.simsimbookstore.frontserver.users.user.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserStatusUpdateRequestDto {
    private UserStatus status;
}
