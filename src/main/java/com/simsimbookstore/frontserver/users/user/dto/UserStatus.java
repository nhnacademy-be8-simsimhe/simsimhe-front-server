package com.simsimbookstore.frontserver.users.user.dto;

import lombok.Getter;

@Getter
public enum UserStatus {
    ACTIVE("활성"),
    QUIT("탈퇴"),
    INACTIVE("휴면");

    private final String name;

    UserStatus(String name) {
        this.name = name;
    }
}
