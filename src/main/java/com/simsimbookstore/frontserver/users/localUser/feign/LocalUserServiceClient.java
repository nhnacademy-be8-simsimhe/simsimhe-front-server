package com.simsimbookstore.frontserver.users.localUser.feign;

import com.simsimbookstore.frontserver.users.localUser.dto.LocalUserRegisterRequest;
import com.simsimbookstore.frontserver.users.localUser.dto.LocalUserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "localUserApi", url = "http://localhost:8000/api/users/localUsers")
public interface LocalUserServiceClient {

    @PostMapping
    String addUser(@RequestBody LocalUserRegisterRequest localUserRequest);

    @GetMapping("/{loginId}")
    LocalUserResponse findByLoginId(@PathVariable String loginId);

    @GetMapping("/{loginId}/exists")
    boolean existsByLoginId(@PathVariable String loginId);
}
