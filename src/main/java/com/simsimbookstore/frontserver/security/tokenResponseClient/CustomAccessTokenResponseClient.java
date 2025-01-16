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
    }
}
