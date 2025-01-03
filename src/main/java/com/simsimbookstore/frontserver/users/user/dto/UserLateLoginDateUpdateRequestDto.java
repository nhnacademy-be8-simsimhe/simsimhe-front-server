package com.simsimbookstore.frontserver.users.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class UserLateLoginDateUpdateRequestDto {

    @NotNull
    private LocalDateTime latestLoginDate;
}
