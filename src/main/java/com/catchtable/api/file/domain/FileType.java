package com.catchtable.api.file.domain;

import com.catchtable.exception.exception.FileException;
import com.catchtable.response.error.FileErrorCode;
import java.util.Set;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FileType {
    CSV(".csv"),
    XLS(".xls"),
    XLSX(".xlsx");
//    MP4(".mp4", null),
//    MKV(".mkv", null),
//    JPG(".jpg", null),
//    PNG(".jng", null);

    public static final Set<FileType> COUPON_UPLOAD_FILE_TYPE = Set.of(CSV, XLS, XLSX);

    private final String extension;

    public static FileType getFileType(String extension) {
        for (FileType fileType : FileType.values()) {
            if (fileType.getExtension()
                        .equalsIgnoreCase(extension)) {
                return fileType;
            }
        }
        throw new FileException(FileErrorCode.UNSUPPORTED_FILE_EXTENSION, extension);
    }

    public static boolean isCouponExtension(FileType fileType) {
        return COUPON_UPLOAD_FILE_TYPE.contains(fileType);
    }
}
