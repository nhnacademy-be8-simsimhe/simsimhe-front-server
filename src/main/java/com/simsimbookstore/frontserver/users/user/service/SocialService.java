package com.simsimbookstore.frontserver.users.user.service;

public interface SocialService {

    String getPaycoUrl();

    String getAccessToken(String code);

    String getUserInfo(String accessToken);

    String logoutPayco(String accessToken);
}
