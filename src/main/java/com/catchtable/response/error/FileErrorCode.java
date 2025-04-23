package com.catchtable.response.error;

import com.catchtable.base.BaseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum FileErrorCode implements BaseCode {
    // 5xxx
    IO_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "5001", "IO Exception"),
    NO_HEADER_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "5002", "IO Exception"),
    EMPTY_FILE(HttpStatus.INTERNAL_SERVER_ERROR, "5003", "IO Exception"),
    SAVE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "5003", "IO Exception"),
    DELETE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "5003", "IO Exception"),
    LOAD_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "5003", "IO Exception"),
    UNSUPPORTED_FILE_EXTENSION(HttpStatus.INTERNAL_SERVER_ERROR, "5004", "Unsupported file extension"),

    // 4xxx
    NULL_FILE_NAME(HttpStatus.BAD_REQUEST, "4001", "File name cannot be null"),
    INVALID_FILE_PATH(HttpStatus.BAD_REQUEST, "4001", "Invalid file path"),
    FILE_NOT_FOUND(HttpStatus.NOT_FOUND, "4004", "File not found"),
    HEADER_NOT_FOUND(HttpStatus.BAD_REQUEST, "4004", "Header not found"),
    ;
    private final HttpStatus httpStatus;
    private final String status;
    private final String message;
}
