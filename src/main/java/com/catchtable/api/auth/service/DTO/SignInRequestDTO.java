package com.catchtable.api.auth.service.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SignInRequestDTO {
    private String userName;

    private String password;
}
