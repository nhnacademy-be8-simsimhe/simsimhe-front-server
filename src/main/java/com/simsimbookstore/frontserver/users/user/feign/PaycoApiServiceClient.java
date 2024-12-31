package com.simsimbookstore.frontserver.users.user.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "paycoApiClient", url = "https://apis-payco.krp.toastoven.net/payco/friends/find_member_v2.json")
public interface PaycoApiServiceClient {

    @PostMapping
    String getUserInfo(
            @RequestHeader(value = "client_id") String clientId,
            @RequestHeader(value = "access_token") String accessToken
    );
}
