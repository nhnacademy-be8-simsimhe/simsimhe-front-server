package com.simsimbookstore.frontserver.user.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LoginRequest {
    private String loginId;
    private String password;
}
