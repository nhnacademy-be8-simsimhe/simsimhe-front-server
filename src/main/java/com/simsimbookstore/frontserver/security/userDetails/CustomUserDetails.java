package com.simsimbookstore.frontserver.security.userDetails;


import com.simsimbookstore.frontserver.users.role.dto.RoleName;
import com.simsimbookstore.frontserver.users.user.dto.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.time.LocalDateTime;
import java.util.*;


@Builder
@Getter
@AllArgsConstructor
public class CustomUserDetails implements UserDetails, OAuth2User {
    private String principalName; // 로컬인 경우 loginId, social인 경우 oauthId
    private String password;
    private Long userId;
    private List<GrantedAuthority> authorities;
    private UserStatus userStatus;
    private LocalDateTime latestLoginDate;
    private boolean isSocial;



    @Override
    public Map<String, Object> getAttributes() {
        return Map.of();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return principalName;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getName() {
        return principalName;
    }

    public void addRole(RoleName role) {
        if (Objects.isNull(authorities)) {
            authorities = new ArrayList<>();
        }
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public boolean isEnabled() {
        if (userStatus.equals(UserStatus.QUIT)) {
            return false;
        }
        else {
            return true;
        }
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAdmin(){
        if (Objects.isNull(authorities)){
            return false;
        }

        return authorities.stream()
                .anyMatch(authority -> "ROLE_ADMIN".equals(authority.getAuthority()));
    }
}
