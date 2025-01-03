package com.simsimbookstore.frontserver.users.localUser.service;

import com.simsimbookstore.frontserver.users.localUser.dto.LocalUserRegisterRequest;
import com.simsimbookstore.frontserver.users.localUser.dto.LocalUserResponse;
import org.springframework.stereotype.Service;

public interface LocalUserService {

    LocalUserResponse findUserByLoginId(String loginId);

    String addLocalUser(LocalUserRegisterRequest localUserRequest);

    boolean existsByLoginId(String loginId);
}
