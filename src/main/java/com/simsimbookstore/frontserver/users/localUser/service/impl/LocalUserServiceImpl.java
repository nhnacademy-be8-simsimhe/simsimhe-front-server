package com.simsimbookstore.frontserver.users.localUser.service.impl;

import com.simsimbookstore.frontserver.users.localUser.dto.LocalUserRegisterRequest;
import com.simsimbookstore.frontserver.users.localUser.dto.LocalUserResponse;
import com.simsimbookstore.frontserver.users.localUser.feign.LocalUserServiceClient;
import com.simsimbookstore.frontserver.users.localUser.service.LocalUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class LocalUserServiceImpl implements LocalUserService {

    private final LocalUserServiceClient localUserServiceClient;
    private final PasswordEncoder passwordEncoder;


    @Override
    public String addLocalUser(LocalUserRegisterRequest localUserRequest) {
        String encode = passwordEncoder.encode(localUserRequest.getPassword());
        localUserRequest.updatePassword(encode);
        return localUserServiceClient.addUser(localUserRequest);
    }

    @Override
    public LocalUserResponse findUserByLoginId(String loginId) {
        if (Objects.isNull(loginId)){
            throw new IllegalArgumentException("loginId cannot be null");
        }
        LocalUserResponse localUserResponse = localUserServiceClient.findByLoginId(loginId);
        return localUserResponse;
    }

    @Override
    public boolean existsByLoginId(String loginId){
        if (Objects.isNull(loginId)){
            throw new IllegalArgumentException("loginId cannot be null");
        }

        return localUserServiceClient.existsByLoginId(loginId);
    }




}
