package com.catchtable.response.success;

import com.catchtable.base.BaseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SuccessCode implements BaseCode {
    SUCCESS(HttpStatus.OK, "2000", "요청이 정상적으로 처리되었습니다."),
    WITHOUT_RESULT(HttpStatus.NO_CONTENT, "2004", null);

    private final HttpStatus httpStatus;
    private final String status;
    private final String message;
}
