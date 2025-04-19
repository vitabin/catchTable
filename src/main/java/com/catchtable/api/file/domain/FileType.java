package com.catchtable.api.file.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FileType {
    CSV(".csv", "csv"),
    XLS(".xls", "excel"),
    XLSX(".xlsx", "excel");
//    MP4(".mp4", null),
//    MKV(".mkv", null),
//    JPG(".jpg", null),
//    PNG(".jng", null);

    private final String extension;
    private final String mimeType;

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
