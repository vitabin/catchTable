package com.catchtable.api.auth.DTO;

import com.catchtable.api.user.domain.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter

@JsonIgnoreProperties(ignoreUnknown = true)
public class SignUpRequestDTO {

    @NotNull
    @Pattern(regexp = "[\\da-zA-Z]{8,20}", message = "유저가 사용할 ID로 공백이 없는 최소 8자리, 최대 20자리 문자열을 입력받습니다.")
    private String userName;

    @NotNull
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\w)\\S{8,20}$", message = "유저가 사용할 비밀번호로 공백이 없는 최소 8자리, 최대 20자리 문자열을 입력받습니다.")
    private String password;

    @NotNull
    private String phoneNumber;

    @NotNull
    private String realName;

    @NotNull
    private String nickName;

    public SignUpParam toParams(UserRole role) {
        return new SignUpParam(userName, password, phoneNumber, realName, nickName, role);
    }

    @Override
    public String toString() {
        return "SignUpRequestDTO{" +
            "userName='" + userName + '\'' +
            "password='" + password + '\'' +
            ", phoneNumber='" + phoneNumber + '\'' +
            ", realName='" + realName + '\'' +
            ", nickName='" + nickName + '\'' +
            '}';
    }
}
