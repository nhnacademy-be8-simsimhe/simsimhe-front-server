package com.simsimbookstore.frontserver.user.service.impl;

import com.simsimbookstore.frontserver.user.request.LocalUserRequest;
import com.simsimbookstore.frontserver.user.feign.UserServiceClient;
import com.simsimbookstore.frontserver.user.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserServiceClient userServiceClient;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserServiceClient userServiceClient,@Lazy PasswordEncoder passwordEncoder) {
        this.userServiceClient = userServiceClient;
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
        return userServiceClient.generateJwt(loginId);
    }
}
