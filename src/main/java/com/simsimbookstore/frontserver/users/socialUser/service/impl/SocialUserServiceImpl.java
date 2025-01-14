package com.simsimbookstore.frontserver.users.socialUser.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.simsimbookstore.frontserver.users.socialUser.dto.PaycoTokenResponseDto;
import com.simsimbookstore.frontserver.users.socialUser.dto.PaycoUserInfoResponseDto;
import com.simsimbookstore.frontserver.users.socialUser.dto.SocialUserRequestDto;
import com.simsimbookstore.frontserver.users.socialUser.dto.SocialUserResponse;
import com.simsimbookstore.frontserver.users.socialUser.feign.SocialUserServiceClient;
import com.simsimbookstore.frontserver.users.user.feign.PaycoApiServiceClient;
import com.simsimbookstore.frontserver.users.user.feign.PaycoAuthServiceClient;
import com.simsimbookstore.frontserver.users.socialUser.service.SocialUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SocialUserServiceImpl implements SocialUserService {

    private final PaycoAuthServiceClient paycoAuthServiceClient;
    private final PaycoApiServiceClient paycoApiServiceClient;
    private final SocialUserServiceClient socialUserServiceClient;

    @Value("${payco.client_id}")
    private String client_id;
    @Value("${payco.client_secret}")
    private String client_secret;
    @Value("${payco.redirect_url}")
    private String redirect_uri;

    private final String paycoUrl = "https://id.payco.com/oauth2.0";
    private final String response_type = "code";
    private final String serviceProviderCode= "FRIENDS";
    private final String userLocale = "ko_KR";

    @Override
    public String getPaycoUrl(){
        StringBuilder url = new StringBuilder(paycoUrl + "/authorize");
        url.append("?response_type=").append(response_type);
        url.append("&client_id=").append(client_id);
        url.append("&redirect_uri=").append(redirect_uri);
        url.append("&serviceProviderCode=").append(serviceProviderCode);
        url.append("&userLocale=").append(userLocale);
        return url.toString();
    }

    @Override
    public PaycoTokenResponseDto generateToken(String code){
        PaycoTokenResponseDto paycoTokenResponseDto = paycoAuthServiceClient.getAccessToken("authorization_code", client_id, client_secret, code);
        return paycoTokenResponseDto;
    }

    @Override
    public PaycoUserInfoResponseDto getUserInfo(String accessToken){
        String userInfoJson = paycoApiServiceClient.getUserInfo(client_id, accessToken);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = null;
        try {
            rootNode = objectMapper.readTree(userInfoJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        JsonNode memberNode = rootNode.path("data").path("member");
        PaycoUserInfoResponseDto paycoUserInfoResponseDto = PaycoUserInfoResponseDto.builder()
                .idNo(memberNode.path("idNo").asText())
                .email(memberNode.path("email").asText())
                .mobile(memberNode.path("mobile").asText())
                .maskedEmail(memberNode.path("maskedEmail").asText())
                .maskedMobile(memberNode.path("maskedMobile").asText())
                .name(memberNode.path("name").asText())
                .genderCode(memberNode.path("genderCode").asText())
                .ageGroup(memberNode.path("ageGroup").asText())
                .birthdayMMdd(memberNode.path("birthdayMMdd").asText())
                .build();

        return paycoUserInfoResponseDto;
    }

    @Override
    public String logoutPayco(String accessToken){
        return paycoAuthServiceClient.logout(accessToken, client_id, client_secret);
    }

    @Override
    public SocialUserResponse loginSocialUser(SocialUserRequestDto socialUserRequestDto){
        SocialUserResponse login = socialUserServiceClient.login(socialUserRequestDto);
        return login;
    }

    @Override
    public SocialUserResponse login(SocialUserRequestDto socialUserRequestDto){
        SocialUserResponse socialUSerResponse = socialUserServiceClient.login(socialUserRequestDto);
        return socialUSerResponse;
    }
}
