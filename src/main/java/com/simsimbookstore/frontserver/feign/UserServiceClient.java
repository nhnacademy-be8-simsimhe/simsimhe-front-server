package com.simsimbookstore.frontserver.feign;

import com.simsimbookstore.frontserver.request.LocalUserRequest;
import com.simsimbookstore.frontserver.request.LoginRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "userApi", url = "http://localhost:8081/api/users")
public interface UserServiceClient {

    @PostMapping
    String addUser(@RequestBody LocalUserRequest localUserRequest);

    @GetMapping("/{userId}")
    String findByUserId(@PathVariable Long userId);

    @GetMapping("/localUsers/{loginId}")
    LocalUserRequest findByLoginId(@PathVariable String loginId);

    @PostMapping("/localUsers/{loginId}/jwt")
    String generateJwt(@PathVariable String loginId);
}
