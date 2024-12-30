package com.simsimbookstore.frontserver.user.feign;

import com.simsimbookstore.frontserver.user.request.LocalUserRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "userApi", url = "http://localhost:8000/api/users")
public interface UserServiceClient {

    @PostMapping("/localUsers")
    String addUser(@RequestBody LocalUserRequest localUserRequest);

    @GetMapping("/{userId}")
    String findByUserId(@PathVariable Long userId);

    @GetMapping("/localUsers/{loginId}")
    LocalUserRequest findByLoginId(@PathVariable String loginId);

    @GetMapping("/localUsers/{loginId}/exists")
    boolean existsByLoginId(@PathVariable String loginId);
}
