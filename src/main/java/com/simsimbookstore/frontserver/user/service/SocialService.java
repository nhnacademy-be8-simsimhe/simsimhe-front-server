package com.simsimbookstore.frontserver.user.service;

public interface SocialService {

    String getPaycoUrl();

    String getAccessToken(String code);

    String getUserInfo(String accessToken);

    String logoutPayco(String accessToken);
}
