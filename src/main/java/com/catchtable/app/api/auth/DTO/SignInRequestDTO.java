package com.catchtable.app.api.auth.DTO;

import com.catchtable.app.api.user.domain.UserEntity;
import lombok.Getter;

@Getter

public class SignInRequestDTO {

    private String userName;

    private String password;

    public UserEntity toEnity() {
        UserEntity userEntity = new UserEntity();
        userEntity.fromDto(this);
        return userEntity;
    }
}
