package com.simsimbookstore.frontserver.users.user.service.impl;

import com.simsimbookstore.frontserver.users.user.dto.UserResponse;
import com.simsimbookstore.frontserver.users.user.feign.JwtServiceClient;
import com.simsimbookstore.frontserver.users.user.feign.UserServiceClient;
import com.simsimbookstore.frontserver.users.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserServiceClient userServiceClient;
    private final JwtServiceClient jwtServiceClient;

    @Override
    public UserResponse findUserByUserId(Long userId) {
        return userServiceClient.findByUserId(userId);
    }

    @Override
    public String generateJwt(String loginId) {
        return jwtServiceClient.generateJwt(loginId);
    }
}
