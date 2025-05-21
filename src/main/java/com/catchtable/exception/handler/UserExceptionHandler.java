package com.catchtable.exception.handler;

import com.catchtable.exception.exception.UserException;
import com.catchtable.response.ErrorResponse;
import com.catchtable.response.error.UserErrorCode;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class UserExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserException.class)
    public ErrorResponse handleUserException(UserException e, HttpServletResponse response) {
        UserErrorCode code = (UserErrorCode) e.getCode();
        response.setStatus(code.getHttpStatus()
                               .value());
        return ErrorResponse.of(code);
    }
}
