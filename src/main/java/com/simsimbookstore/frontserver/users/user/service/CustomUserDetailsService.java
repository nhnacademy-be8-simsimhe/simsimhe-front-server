package com.simsimbookstore.frontserver.users.user.service;

import com.simsimbookstore.frontserver.security.userDetails.CustomUserDetails;
import com.simsimbookstore.frontserver.users.localUser.dto.LocalUserResponse;
import com.simsimbookstore.frontserver.users.localUser.service.LocalUserService;
import com.simsimbookstore.frontserver.users.role.dto.RoleName;
import org.springframework.context.annotation.Lazy;
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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LocalUserResponse localUserResponse = localUserService.findUserByLoginId(username);

        if (Objects.isNull(localUserResponse)) {
            throw new UsernameNotFoundException("not founded user with: " + username);
        }
        CustomUserDetails customUserDetails = CustomUserDetails.builder()
                .userId(localUserResponse.getUserId())
                .loginId(localUserResponse.getLoginId())
                .password(localUserResponse.getPassword())
                .authorities(new ArrayList<>())
                .build();

//        for (RoleName role : localUserResponse.getRoles()){
//            customUserDetails.addRole(role);
//        }
        return customUserDetails;
    }
}
