package com.catchtable.exception.handler;

import com.catchtable.exception.exception.SegmentException;
import com.catchtable.response.ErrorResponse;
import com.catchtable.response.error.SegmentErrorCode;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class SegmentExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(SegmentException.class)
    public ErrorResponse handleCustomException(SegmentException e, HttpServletResponse response) {
        SegmentErrorCode error = (SegmentErrorCode) e.getCode();
        response.setStatus(error.getHttpStatus()
                                .value());
        return ErrorResponse.of(error);
    }
}
