package com.catchtable.response.error;

import com.catchtable.base.BaseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements BaseCode {
    USER_NOT_FOUND(4201, "User not found."),
    ;
    private final int status;
    private final String message;
}
