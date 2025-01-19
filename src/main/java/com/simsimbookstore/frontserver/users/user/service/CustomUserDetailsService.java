package com.simsimbookstore.frontserver.users.user.service;

import com.simsimbookstore.frontserver.security.userDetails.CustomUserDetails;
import com.simsimbookstore.frontserver.users.localUser.dto.LocalUserResponseDto;
import com.simsimbookstore.frontserver.users.localUser.service.LocalUserService;
import com.simsimbookstore.frontserver.users.role.dto.RoleName;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final LocalUserService localUserService;

    public CustomUserDetailsService(@Lazy LocalUserService localUserService) {
        this.localUserService = localUserService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws AuthenticationException {
        LocalUserResponseDto localUserResponse = localUserService.findUserByLoginId(username);

        if (Objects.isNull(localUserResponse)) {
            throw new UsernameNotFoundException("not founded user with loginId: " + username);
        }

        CustomUserDetails customUserDetails = CustomUserDetails.builder()
                .userId(localUserResponse.getUserId())
                .principalName(localUserResponse.getLoginId())
                .password(localUserResponse.getPassword())
                .authorities(new ArrayList<>())
                .userStatus(localUserResponse.getUserStatus())
                .latestLoginDate(localUserResponse.getLatestLoginDate())
                .isSocial(false)
                .build();

        for (RoleName role : localUserResponse.getRoles()){
            customUserDetails.addRole(role);
        }
        return customUserDetails;
    }
}
