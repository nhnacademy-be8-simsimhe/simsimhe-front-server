package com.simsimbookstore.frontserver.users.user.feign;

import com.simsimbookstore.frontserver.users.user.dto.UserLateLoginDateUpdateRequestDto;
import com.simsimbookstore.frontserver.users.user.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@FeignClient(name = "userApi", url = "http://localhost:8000/api/users")
public interface UserServiceClient {

    @GetMapping("/{userId}")
    UserResponse findByUserId(@PathVariable Long userId);

    @PutMapping("/{userId}/latestLoginDate")
    UserResponse updateLatestLoginDate(@PathVariable Long userId, @RequestBody UserLateLoginDateUpdateRequestDto requestDto);
}
