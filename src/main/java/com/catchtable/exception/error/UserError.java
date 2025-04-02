package com.catchtable.exception.error;

import com.catchtable.base.BaseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserError implements BaseCode {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "User not found."),
    ;
    private final int code;
    private final String message;
}
