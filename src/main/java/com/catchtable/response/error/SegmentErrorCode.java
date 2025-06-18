package com.catchtable.response.error;

import com.catchtable.base.BaseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SegmentErrorCode implements BaseCode {
    NOT_FOUND(HttpStatus.NOT_FOUND, "4001", "Segment Not Founded"),
    UNEXPECTED_PARAM(HttpStatus.BAD_REQUEST, "4004", "Unexpected parameter"),
    ;

    private final HttpStatus httpStatus;
    private final String status;
    private final String message;
}
