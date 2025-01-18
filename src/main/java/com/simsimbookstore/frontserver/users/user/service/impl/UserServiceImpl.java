package com.simsimbookstore.frontserver.users.user.service.impl;

import com.simsimbookstore.frontserver.users.user.dto.*;
import com.simsimbookstore.frontserver.users.user.feign.JwtServiceClient;
import com.simsimbookstore.frontserver.users.user.feign.UserServiceClient;
import com.simsimbookstore.frontserver.users.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public String generateJwt(JwtGenerateRequestDto requestDto) {
        return jwtServiceClient.generateJwt(requestDto);
    }

    @Override
    public UserResponse updateUserLatestLoginDate(Long loginId, UserLateLoginDateUpdateRequestDto requestDto) {
        return userServiceClient.updateLatestLoginDate(loginId,requestDto);
    }

    @Override
    public UserResponse updateUserStatus(Long loginId, UserStatusUpdateRequestDto userStatusUpdateRequestDto) {
        return userServiceClient.updateUserStatus(loginId,userStatusUpdateRequestDto);
    }

    @Override
    public List<UserResponse> getActiveUser() {
        return userServiceClient.getActiveUser();
    }
    @Override
    public List<UserResponse> getAllUserByBirth(String birthMonth) {
        return userServiceClient.getAllUserByBirth(birthMonth);
    }

}
