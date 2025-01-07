package com.simsimbookstore.frontserver.users.localUser.service;

import com.simsimbookstore.frontserver.users.localUser.dto.LocalUserRegisterRequestDto;
import com.simsimbookstore.frontserver.users.localUser.dto.LocalUserResponseDto;

public interface LocalUserService {

    LocalUserResponseDto findUserByLoginId(String loginId);

    String addLocalUser(LocalUserRegisterRequestDto localUserRequest);

    boolean existsByLoginId(String loginId);
}
