package com.simsimbookstore.frontserver.users.user.service.impl;

import com.simsimbookstore.frontserver.users.user.feign.JwtServiceClient;
import com.simsimbookstore.frontserver.users.user.request.LocalUserRequest;
import com.simsimbookstore.frontserver.users.user.feign.UserServiceClient;
import com.simsimbookstore.frontserver.users.user.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserServiceClient userServiceClient;
    private final JwtServiceClient jwtServiceClient;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserServiceClient userServiceClient, JwtServiceClient jwtServiceClient, @Lazy PasswordEncoder passwordEncoder) {
        this.userServiceClient = userServiceClient;
        this.jwtServiceClient = jwtServiceClient;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String addLocalUser(LocalUserRequest localUserRequest) {
        String encode = passwordEncoder.encode(localUserRequest.getPassword());
        localUserRequest.setPassword(encode);
        return userServiceClient.addUser(localUserRequest);
    }

    @Override
    public String findUserByUserId(Long userId) {
        return userServiceClient.findByUserId(userId);
    }

    @Override
    public LocalUserRequest findUserByLoginId(String loginId) {
        LocalUserRequest localUserRequest = userServiceClient.findByLoginId(loginId);
        return localUserRequest;
    }

    @Override
    public boolean existsByLoginId(String loginId){
        return userServiceClient.existsByLoginId(loginId);
    }

    @Override
    public String generateJwt(String loginId) {
        return jwtServiceClient.generateJwt(loginId);
    }
}
