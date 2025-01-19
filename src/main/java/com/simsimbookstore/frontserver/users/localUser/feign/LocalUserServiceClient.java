package com.simsimbookstore.frontserver.users.localUser.feign;

import com.simsimbookstore.frontserver.config.AuthenticationFeignConfig;
import com.simsimbookstore.frontserver.users.localUser.dto.LocalUserRegisterRequestDto;
import com.simsimbookstore.frontserver.users.localUser.dto.LocalUserResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "localUserApi", url = "http://localhost:8000/api/users/localUsers", configuration = AuthenticationFeignConfig.class)
public interface LocalUserServiceClient {

    @PostMapping
    String addUser(@RequestBody LocalUserRegisterRequestDto localUserRequest);

    @GetMapping("/{loginId}")
    LocalUserResponseDto findByLoginId(@PathVariable String loginId);

    @GetMapping("/{loginId}/exists")
    boolean existsByLoginId(@PathVariable String loginId);
}
