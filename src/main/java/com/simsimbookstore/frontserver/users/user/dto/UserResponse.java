package com.simsimbookstore.frontserver.users.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.simsimbookstore.frontserver.users.role.dto.RoleName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserResponse {
    private Long userId;

    private String userName;

    private String mobileNumber;

    private String email;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birth;

    private Gender gender;

    private LocalDateTime createdAt;

    private List<RoleName> roles;
}
