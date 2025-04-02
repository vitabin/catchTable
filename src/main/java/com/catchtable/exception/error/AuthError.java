package com.catchtable.exception.error;

import com.catchtable.base.BaseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthError implements BaseCode {
    TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "토큰을 찾지 못 했습니다."),
    WRONG_USERNAME_OR_PASSWORD(HttpStatus.NOT_FOUND.value(), "틀린 아이디 또는 비밀번호입니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED.value(), "유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED.value(), "만료된 토큰입니다."),
    ;

    private final int code;
    private final String message;
}
