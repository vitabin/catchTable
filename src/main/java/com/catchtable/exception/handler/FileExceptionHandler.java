package com.catchtable.exception.handler;

import com.catchtable.exception.exception.FileException;
import com.catchtable.response.ErrorResponse;
import com.catchtable.response.error.FileErrorCode;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class FileExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(FileException.class)
    public ErrorResponse handleFileException(FileException e, HttpServletResponse response) {
        FileErrorCode error = (FileErrorCode) e.getCode();
        response.setStatus(error.getHttpStatus()
                                .value());

        if (e.getPath() != null) {
            return ErrorResponse.of(error, String.format(
                error.getMessage() + " : [%s]", e.getPath()));
        }

        if (e.getFilename() != null) {
            return ErrorResponse.of(error, String.format(
                error.getMessage() + " : [%s]", e.getFilename()));
        }
        return ErrorResponse.of(error);
    }
}
