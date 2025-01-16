package com.simsimbookstore.frontserver.users.user.feign;

import com.simsimbookstore.frontserver.users.user.dto.JwtGenerateRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "authServer", url = "http://localhost:8000/auth")
public interface JwtServiceClient {

    @PostMapping("users/jwt")
    String generateJwt(@RequestBody JwtGenerateRequestDto requestDto);
}
