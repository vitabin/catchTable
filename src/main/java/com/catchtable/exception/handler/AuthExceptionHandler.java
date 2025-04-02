package com.catchtable.exception.handler;

import com.catchtable.api.auth.controller.AuthController;
import com.catchtable.exception.error.AuthError;
import com.catchtable.exception.response.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import java.security.InvalidParameterException;
import java.util.NoSuchElementException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice(assignableTypes = {AuthController.class})
public class AuthExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ExpiredJwtException.class)
    public ErrorResponse handleExpiredJwtException(ExpiredJwtException e) {
        AuthError error = AuthError.EXPIRED_TOKEN;
        return ErrorResponse.create(error);
    }

    @ExceptionHandler(JwtException.class)
    public ErrorResponse handleJwtException(JwtException e) {
        AuthError error = AuthError.INVALID_TOKEN;
        return ErrorResponse.create(error);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ErrorResponse handleNoSuchElementException(NoSuchElementException e) {
        AuthError error = AuthError.WRONG_USERNAME_OR_PASSWORD;
        return ErrorResponse.create(error);
    }

    @ExceptionHandler(InvalidParameterException.class)
    public ErrorResponse handleInvalidParameterException(InvalidParameterException e) {
        AuthError error = AuthError.INVALID_TOKEN;
        return ErrorResponse.create(error);
    }
}
