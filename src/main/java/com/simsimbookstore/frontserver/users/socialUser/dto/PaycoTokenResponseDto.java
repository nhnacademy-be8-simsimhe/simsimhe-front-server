package com.simsimbookstore.frontserver.users.socialUser.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaycoTokenResponseDto {
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("access_token_secret")
    private String accessTokenSecret;
    @JsonProperty("refresh_token")
    private String refreshToken;
    @JsonProperty("token_type")
    private String tokenType;
    @JsonProperty("expires_in")
    private String expiresIn;
    @JsonProperty("state")
    private String state;
}
