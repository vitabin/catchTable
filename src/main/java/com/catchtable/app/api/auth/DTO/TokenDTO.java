package com.catchtable.app.api.auth.DTO;

import lombok.Getter;

@Getter

public class TokenDTO {

    private String accessToken;

    private String refreshToken;

    public TokenDTO of(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        return this;
    }
}
