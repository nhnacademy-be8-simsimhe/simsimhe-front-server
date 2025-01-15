package com.simsimbookstore.frontserver.users.socialUser.service;

import com.simsimbookstore.frontserver.users.socialUser.dto.PaycoTokenResponseDto;
import com.simsimbookstore.frontserver.users.socialUser.dto.PaycoUserInfoResponseDto;
import com.simsimbookstore.frontserver.users.socialUser.dto.SocialUserRequestDto;
import com.simsimbookstore.frontserver.users.socialUser.dto.SocialUserResponse;

public interface SocialUserService {

    String getPaycoUrl();

    PaycoTokenResponseDto generateToken(String code);

    PaycoUserInfoResponseDto getUserInfo(String accessToken);

    String logoutPayco(String accessToken);

    SocialUserResponse loginSocialUser(SocialUserRequestDto socialUserRequestDto);

    SocialUserResponse login(SocialUserRequestDto socialUserRequestDto);
}
