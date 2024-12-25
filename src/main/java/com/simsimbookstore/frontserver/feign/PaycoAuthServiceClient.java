package com.simsimbookstore.frontserver.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "paycoAuthClient",url = "https://id.payco.com/oauth2.0/")
public interface PaycoAuthServiceClient {

    @PostMapping("/token")
    String getAccessToken(
            @RequestParam(value = "grant_type") String grant_type,
            @RequestParam(value = "client_id") String clientId,
            @RequestParam(value = "client_secret") String client_secret,
            @RequestParam(value = "code") String code
    );

    @PostMapping("/logout")
    String logout(
            @RequestParam(value = "token") String accessToken,
            @RequestParam(value = "client_id") String clientId,
            @RequestParam(value = "client_secret") String clientSecret
    );


}
