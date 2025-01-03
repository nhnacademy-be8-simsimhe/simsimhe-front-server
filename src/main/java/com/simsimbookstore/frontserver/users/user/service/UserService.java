package com.simsimbookstore.frontserver.users.user.service;

import com.simsimbookstore.frontserver.users.user.dto.UserLateLoginDateUpdateRequestDto;
import com.simsimbookstore.frontserver.users.user.dto.UserResponse;

import java.time.LocalDateTime;

public interface UserService {

    UserResponse findUserByUserId(Long userId);

    String generateJwt(String loginId);

    UserResponse updateUserLatestLoginDate(Long loginId, UserLateLoginDateUpdateRequestDto requestDto);
}
