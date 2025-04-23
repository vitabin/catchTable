package com.catchtable.response.error;

import com.catchtable.base.BaseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements BaseCode {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "4201", "User not found."),
    ;
    private final HttpStatus httpStatus;
    private final String status;
    private final String message;
}
