package com.simsimbookstore.frontserver.users.localUser.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.simsimbookstore.frontserver.users.role.dto.RoleName;
import com.simsimbookstore.frontserver.users.user.dto.Gender;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class LocalUserRegisterRequest {
    private String userName;

    private String mobileNumber;

    private String email;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birth;

    private Gender gender;

    private RoleName roleName = RoleName.USER;

    private String loginId;

    private String password;

    public void updatePassword(String password) {
        this.password = password;
    }
}


