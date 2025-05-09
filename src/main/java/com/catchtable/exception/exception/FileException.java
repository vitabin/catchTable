package com.catchtable.exception.exception;

import com.catchtable.base.BaseCode;
import java.nio.file.Path;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FileException extends RuntimeException {

    private final BaseCode code;
    private Path path;
    private String filename;

    public FileException(BaseCode code, Path path) {
        this.code = code;
        this.path = path;
    }

    public FileException(BaseCode code, String filename) {
        this.code = code;
        this.filename = filename;
    }
}
