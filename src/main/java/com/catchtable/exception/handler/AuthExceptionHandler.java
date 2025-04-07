package com.catchtable.exception.handler;

import com.catchtable.exception.exception.AuthException;
import com.catchtable.response.ErrorResponse;
import com.catchtable.response.error.AuthErrorCode;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class AuthExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AuthException.class)
    public ErrorResponse handleCustomException(AuthException ce, HttpServletResponse response) {
        AuthErrorCode error = (AuthErrorCode) ce.getCode();
        response.setStatus(error.getStatus());
        return ErrorResponse.of(error);
    }
}
