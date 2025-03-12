package com.catchtable.api.auth.DTO;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SignInUserDTO {
    private String userName;

    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\w).{8,20}$")
    private String password;

    private String phoneNumber;

    private String realName;

    private String nickName;
}
