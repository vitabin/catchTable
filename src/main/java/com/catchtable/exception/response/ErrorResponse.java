package com.catchtable.exception.response;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ErrorResponse {
    private int status;
    private String message;
    private LocalDateTime timestamp;
    private String path;

    public ErrorResponse(int status, String message, String path) {
        status = status;
        message = message;
        timestamp = LocalDateTime.now();
        path = path;
    }
}
