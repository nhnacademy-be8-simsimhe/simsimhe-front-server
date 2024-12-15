package com.simsimbookstore.frontserver.service;

import com.simsimbookstore.frontserver.request.LocalUserRequest;
import com.simsimbookstore.frontserver.request.LoginRequest;

public interface UserService {
    String addLocalUser(LocalUserRequest localUserRequest);

    String findUserByUserId(Long userId);

    LocalUserRequest findUserByLoginId(String loginId);

    String generateJwt(String loginId);
}
