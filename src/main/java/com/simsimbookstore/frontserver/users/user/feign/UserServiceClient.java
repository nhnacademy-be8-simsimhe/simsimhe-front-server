package com.simsimbookstore.frontserver.users.user.feign;

import com.simsimbookstore.frontserver.users.user.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "userApi", url = "http://localhost:8000/api/users")
public interface UserServiceClient {



    @GetMapping("/{userId}")
    UserResponse findByUserId(@PathVariable Long userId);


}
