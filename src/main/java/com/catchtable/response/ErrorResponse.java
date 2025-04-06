package com.catchtable.response;

import com.catchtable.base.BaseCode;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ErrorResponse {

    private int status;
    private String error;
    private String message;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;

    public static ErrorResponse create(BaseCode error) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.status = error.getStatus();
        errorResponse.error = error.name();
        errorResponse.message = error.getMessage();
        errorResponse.timestamp = LocalDateTime.now();

        return errorResponse;
    }
}
