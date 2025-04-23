package com.catchtable.exception.exception;

import com.catchtable.base.BaseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserException extends RuntimeException {

    private final BaseCode code;
}
