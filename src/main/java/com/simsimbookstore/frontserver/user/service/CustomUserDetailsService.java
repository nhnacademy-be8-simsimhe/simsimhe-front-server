package com.simsimbookstore.frontserver.user.service;

import com.simsimbookstore.frontserver.user.request.LocalUserRequest;
import com.simsimbookstore.frontserver.security.userDetails.CustomUserDetails;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;

    public CustomUserDetailsService(@Lazy UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LocalUserRequest localUser = userService.findUserByLoginId(username);

        if (Objects.isNull(localUser)) {
            throw new UsernameNotFoundException("not founded user with: " + username);
        }
        CustomUserDetails customUserDetails = new CustomUserDetails(
                localUser.getLoginId(),localUser.getPassword()
        );
        return customUserDetails;
    }
}
