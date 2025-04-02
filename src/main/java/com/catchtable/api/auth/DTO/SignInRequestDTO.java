package com.catchtable.api.auth.DTO;

import com.catchtable.api.user.domain.UserEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInRequestDTO {

    private String userName;

    private String password;

    public UserEntity toEnity() {
        UserEntity userEntity = new UserEntity();
        userEntity.fromDto(this);
        return userEntity;
    }
}
