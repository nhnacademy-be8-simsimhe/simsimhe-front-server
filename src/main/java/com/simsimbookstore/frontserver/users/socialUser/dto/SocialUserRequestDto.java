package com.simsimbookstore.frontserver.users.socialUser.dto;

import com.simsimbookstore.frontserver.users.socialUser.dto.Provider;
import com.simsimbookstore.frontserver.users.user.dto.Gender;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class SocialUserRequestDto {
    private String oauthId;
    private String email;
    private String mobile;
    private String name;
    private Gender gender;
    private Provider provider;
}
