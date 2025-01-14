package com.simsimbookstore.frontserver.users.socialUser.dto;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class PaycoUserInfoResponseDto {
    private String idNo;
    private String email;
    private String mobile;
    private String maskedEmail;
    private String maskedMobile;
    private String name;
    private String genderCode;
    private String ageGroup;
    private String birthdayMMdd;
}
