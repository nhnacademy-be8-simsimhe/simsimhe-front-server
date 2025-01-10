package com.simsimbookstore.frontserver.users.user.exception;

import org.springframework.security.core.AuthenticationException;

public class CustomAccountExpiredException extends AuthenticationException {
    private final Long userId;

    public CustomAccountExpiredException(String message, Long userId) {
        super(message);
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }
}
