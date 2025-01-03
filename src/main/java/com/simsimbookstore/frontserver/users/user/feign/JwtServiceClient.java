package com.simsimbookstore.frontserver.users.user.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "authServer", url = "http://localhost:8000/auth")
public interface JwtServiceClient {

    @PostMapping("users/localUsers/{loginId}/jwt")
    String generateJwt(@PathVariable String loginId);
}
