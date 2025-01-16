package com.simsimbookstore.frontserver.users.user.service;

import com.simsimbookstore.frontserver.users.user.dto.*;

import java.time.LocalDateTime;

public interface UserService {

    UserResponse findUserByUserId(Long userId);

    String generateJwt(JwtGenerateRequestDto requestDto);

    UserResponse updateUserLatestLoginDate(Long loginId, UserLateLoginDateUpdateRequestDto requestDto);

    UserResponse updateUserStatus(Long loginId,  UserStatusUpdateRequestDto userStatusUpdateRequestDto);
}
