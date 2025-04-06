package com.catchtable.exception.handler;

import com.catchtable.exception.error.AuthError;
import com.catchtable.exception.exception.AuthException;
import com.catchtable.response.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class AuthExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(AuthException ce) {
        AuthError error = (AuthError) ce.getCode();
        return ResponseEntity.status(error.getHttpStatus())
                             .body(ErrorResponse.create(error));
    }
}
