package com.simsimbookstore.frontserver.users.user.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long userId;
    private String userName;
    private String email;
    private String mobileNumber;
    private LocalDate birth;
    private String gender; // Enum -> String
    private String userStatus; // Enum -> String
    private LocalDateTime createdAt;
    private LocalDateTime latestLoginDate;
    private boolean isSocialLogin;
    private String grade; // Grade 정보에서 tier만 전달
}
