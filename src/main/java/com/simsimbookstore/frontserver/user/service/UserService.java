package com.simsimbookstore.frontserver.user.service;

import com.simsimbookstore.frontserver.user.request.LocalUserRequest;

public interface UserService {
    String addLocalUser(LocalUserRequest localUserRequest);

    String findUserByUserId(Long userId);

    LocalUserRequest findUserByLoginId(String loginId);

    boolean existsByLoginId(String loginId);

    String generateJwt(String loginId);
}
