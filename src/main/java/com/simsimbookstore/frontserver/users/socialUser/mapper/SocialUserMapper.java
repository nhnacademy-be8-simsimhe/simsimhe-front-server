package com.simsimbookstore.frontserver.users.socialUser.mapper;

import com.simsimbookstore.frontserver.users.socialUser.dto.PaycoUserInfoResponseDto;
import com.simsimbookstore.frontserver.users.socialUser.dto.Provider;
import com.simsimbookstore.frontserver.users.socialUser.dto.SocialUserRequestDto;
import com.simsimbookstore.frontserver.users.user.dto.Gender;

public class SocialUserMapper {

    public static SocialUserRequestDto toSocialUSerRequestDto(PaycoUserInfoResponseDto paycoUserInfoResponseDto){
        return SocialUserRequestDto.builder()
                .oauthId(paycoUserInfoResponseDto.getIdNo())
                .email(paycoUserInfoResponseDto.getEmail())
                .mobile(paycoUserInfoResponseDto.getMobile())
                .name(paycoUserInfoResponseDto.getName())
                .gender(paycoUserInfoResponseDto.getGenderCode() == "FEMALE" ? Gender.FEMALE : Gender.MALE)
                .provider(Provider.PAYCO)
                .build();
    }
}
