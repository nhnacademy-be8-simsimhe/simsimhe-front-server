package com.simsimbookstore.frontserver.security.tokenResponseClient;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.simsimbookstore.frontserver.users.socialUser.dto.PaycoTokenResponseDto;
import com.simsimbookstore.frontserver.users.socialUser.dto.SocialUserRequestDto;
import com.simsimbookstore.frontserver.users.user.dto.Gender;
import com.simsimbookstore.frontserver.users.user.feign.PaycoAuthServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class CustomAccessTokenResponseClient implements OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> {

    private final PaycoAuthServiceClient paycoAuthServiceClient;

    @Override
    public OAuth2AccessTokenResponse getTokenResponse(OAuth2AuthorizationCodeGrantRequest grantRequest) {

        //payco의 경우
        if(grantRequest.getClientRegistration().getRegistrationId().equals("payco")){

            PaycoTokenResponseDto authorizationCode = paycoAuthServiceClient.getAccessToken(
                    "authorization_code",
                    grantRequest.getClientRegistration().getClientId(),
                    grantRequest.getClientRegistration().getClientSecret(),
                    grantRequest.getAuthorizationExchange().getAuthorizationResponse().getCode()
            );

            return OAuth2AccessTokenResponse
                    .withToken(authorizationCode.getAccessToken())
                    .refreshToken(authorizationCode.getRefreshToken())
                    .tokenType(OAuth2AccessToken.TokenType.BEARER)
                    .expiresIn(Long.parseLong(authorizationCode.getExpiresIn()))
                    .build();
        }

        return null;
        //나머지는 디폴트로
    }

//    private OAuth2AccessTokenResponse makePaycoToken(OAuth2AuthorizationCodeGrantRequest grantRequest){
//        String tokenResponse = paycoAuthServiceClient.getAccessToken(
//                "authorization_code",
//                grantRequest.getClientRegistration().getClientId(),
//                grantRequest.getClientRegistration().getClientSecret(),
//                grantRequest.getAuthorizationExchange().getAuthorizationResponse().getCode()
//        );
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        JsonNode rootNode = null;
//        try {
//            rootNode = objectMapper.readTree(userInfoJson);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//        JsonNode memberNode = rootNode.path("data").path("member");
//        String genderCode = memberNode.path("genderCode").asText();
//
//        Gender gender = null;
//        if (Objects.nonNull(genderCode) && !genderCode.isEmpty()) {
//            gender = Gender.valueOf(genderCode);
//        }
//
//        SocialUserRequestDto socialUserRequestDto = SocialUserRequestDto.builder()
//                .oauthId(memberNode.path("idNo").asText())
//                .email(memberNode.path("email").asText())
//                .mobile(memberNode.path("mobile").asText())
//                .name(memberNode.path("name").asText())
//                .gender(gender)
//                .build();
//
//
//        return OAuth2AccessTokenResponse
//                .withToken(tokenResponse.accessToken())
//                .refreshToken(tokenResponse.refreshToken())
//                .expiresIn(Long.parseLong(tokenResponse.expiresIn()))
//                .tokenType(OAuth2AccessToken.TokenType.BEARER)
//                .build();
//    }
}
