package com.simsimbookstore.frontserver.users.socialUser.feign;

import com.simsimbookstore.frontserver.users.socialUser.dto.SocialUserRequestDto;
import com.simsimbookstore.frontserver.users.socialUser.dto.SocialUserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "socialUser-api", url = "http://localhost:8000/api/users/socialUser")
public interface SocialUserServiceClient {

    @PostMapping("/login")
    SocialUserResponse login(@RequestBody SocialUserRequestDto socialUserRequest);
}
