package com.catchtable.api.file.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FileType {
    CSV(".csv"),
    XLS(".xls"),
    XLSX(".xlsx"),
    MP4(".mp4"),
    MKV(".mkv"),
    JPG(".jpg"),
    PNG(".jng");

    private final String extension;

    public static FileType getFileType(String extension) {
        for (FileType fileType : FileType.values()) {
            if (fileType.getExtension()
                        .equalsIgnoreCase(extension)) {
                return fileType;
            }
        }
        return null;
    }
}
