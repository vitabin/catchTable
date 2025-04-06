package com.catchtable.exception.error;

import com.catchtable.base.BaseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserError implements BaseCode {
    USER_NOT_FOUND(4201, "User not found."),
    ;
    private final int status;
    private final String message;
}
