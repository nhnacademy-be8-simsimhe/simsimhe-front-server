package com.simsimbookstore.frontserver.users.user.service;

import com.simsimbookstore.frontserver.users.user.dto.UserResponse;

public interface UserService {

    UserResponse findUserByUserId(Long userId);

    String generateJwt(String loginId);
}
