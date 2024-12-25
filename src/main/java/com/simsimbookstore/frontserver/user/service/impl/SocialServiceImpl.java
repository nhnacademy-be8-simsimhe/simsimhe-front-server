package com.simsimbookstore.frontserver.user.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.simsimbookstore.frontserver.user.feign.PaycoApiServiceClient;
import com.simsimbookstore.frontserver.user.feign.PaycoAuthServiceClient;
import com.simsimbookstore.frontserver.user.service.SocialService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SocialServiceImpl implements SocialService {

    private final PaycoAuthServiceClient paycoAuthServiceClient;
    private final PaycoApiServiceClient paycoApiServiceClient;

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

    public SocialServiceImpl(PaycoAuthServiceClient paycoLoginServiceClient, PaycoApiServiceClient paycoApiServiceClient) {
        this.paycoAuthServiceClient = paycoLoginServiceClient;
        this.paycoApiServiceClient = paycoApiServiceClient;
    }


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
    public String getAccessToken(String code){
        String json = paycoAuthServiceClient.getAccessToken("authorization_code", client_id, client_secret, code);
        String accessToken = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(json);
            accessToken=jsonNode.get("access_token").asText();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return accessToken;
    }

    @Override
    public String getUserInfo(String accessToken){
        String userInfo = paycoApiServiceClient.getUserInfo(client_id, accessToken);
        return userInfo;
    }

    @Override
    public String logoutPayco(String accessToken){
        return paycoAuthServiceClient.logout(accessToken, client_id, client_secret);
    }
}
