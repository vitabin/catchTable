package com.catchtable.api.auth.service.DTO;

import com.catchtable.api.user.domain.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TokenDTO {
    private String accessToken;

    private String refreshToken;

    public static TokenDTO create(String accessToken, String refreshToken) {
        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.accessToken = accessToken;
        tokenDTO.refreshToken = refreshToken;

        return tokenDTO;
    }
}
