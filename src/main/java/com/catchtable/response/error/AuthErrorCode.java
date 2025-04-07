package com.catchtable.response.error;

import com.catchtable.base.BaseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements BaseCode {
    TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, 4104, "토큰을 찾지 못 했습니다."),
    WRONG_USERNAME_OR_PASSWORD(HttpStatus.UNAUTHORIZED, 4101, "틀린 아이디 또는 비밀번호입니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, 4102, "유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, 4104, "만료된 토큰입니다."),
    ;

    private final HttpStatus httpStatus;
    private final int status;
    private final String message;

}
