package com.simsimbookstore.frontserver.users.user.service;

import com.simsimbookstore.frontserver.security.userDetails.CustomUserDetails;
import com.simsimbookstore.frontserver.users.role.dto.RoleName;
import com.simsimbookstore.frontserver.users.socialUser.dto.PaycoUserInfoResponseDto;
import com.simsimbookstore.frontserver.users.socialUser.dto.Provider;
import com.simsimbookstore.frontserver.users.socialUser.dto.SocialUserRequestDto;
import com.simsimbookstore.frontserver.users.socialUser.dto.SocialUserResponse;
import com.simsimbookstore.frontserver.users.socialUser.mapper.SocialUserMapper;
import com.simsimbookstore.frontserver.users.socialUser.service.SocialUserService;
import com.simsimbookstore.frontserver.users.user.feign.PaycoApiServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomOauth2UserService extends DefaultOAuth2UserService {
    private final PaycoApiServiceClient paycoApiServiceClient;
    private final SocialUserService socialUserService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        PaycoUserInfoResponseDto userInfo = socialUserService.getUserInfo(userRequest.getAccessToken().getTokenValue());

        SocialUserRequestDto socialUSerRequestDto = SocialUserMapper.toSocialUSerRequestDto(userInfo);

        SocialUserResponse socialUserResponse = socialUserService.login(socialUSerRequestDto);

        CustomUserDetails customUserDetails = CustomUserDetails.builder()
                .principalName(socialUserResponse.getOauthId())
                .userId(socialUserResponse.getUserId())
                .userStatus(socialUserResponse.getUserStatus())
                .latestLoginDate(socialUserResponse.getLatestLoginDate())
                .build();

        for (RoleName role : socialUserResponse.getRoles()) {
            customUserDetails.addRole(role);
        }

        return customUserDetails;
    }
}
