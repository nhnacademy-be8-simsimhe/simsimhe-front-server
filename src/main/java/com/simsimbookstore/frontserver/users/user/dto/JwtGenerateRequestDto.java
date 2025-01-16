package com.simsimbookstore.frontserver.users.user.dto;

import lombok.*;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Setter
@Getter
public class JwtGenerateRequestDto {
    private Long userId;
    private String subject;
    private boolean isSocial;
}
