package com.catchtable.exception.response;

import com.catchtable.exception.error.AuthError;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ErrorResponse {

    private String status;
    private String message;
    private int code;
    private LocalDateTime timestamp;

    public static ErrorResponse create(AuthError error) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.status = error.name();
        errorResponse.message = error.getMessage();
        errorResponse.code = error.getCode();
        errorResponse.timestamp = LocalDateTime.now();

        return errorResponse;
    }
}
