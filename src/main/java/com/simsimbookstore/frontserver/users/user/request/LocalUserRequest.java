package com.simsimbookstore.frontserver.users.user.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class LocalUserRequest {
    private String userName;

    private String mobileNumber;

    private String email;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birth;

    private Gender gender;

    private LocalDateTime createdAt = LocalDateTime.now();

    private RoleName roleName = RoleName.USER;

    private String loginId;

    private String password;

    public enum Gender{
        MALE,
        FEMALE
    }

    public enum RoleName{
        USER,
        ADMIN,
        GUEST
    }
}

