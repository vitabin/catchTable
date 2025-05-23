package com.catchtable.app.api.file.domain;

import com.catchtable.exception.exception.FileException;
import com.catchtable.response.error.FileErrorCode;
import java.util.Set;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;

@Getter
@RequiredArgsConstructor
public enum FileType {
    CSV("csv"),
    XLS("xls"),
    XLSX("xlsx"),
    MP4("mp4"),
    MKV("mkv"),
    JPG("jpg"),
    JPEG("jpeg"),
    PNG("png");

    public static final Set<FileType> COUPON_UPLOAD_FILE_TYPE = Set.of(CSV, XLS, XLSX);

    private final String extension;

    public static FileType getFileType(String filename) {
        String extension = FilenameUtils.getExtension(filename);

        for (FileType fileType : FileType.values()) {
            if (fileType.getExtension()
                        .equalsIgnoreCase(extension)) {
                return fileType;
            }
        }
        throw new FileException(FileErrorCode.UNSUPPORTED_FILE_EXTENSION, filename);
    }

    public static boolean isCouponExtension(FileType fileType) {
        return COUPON_UPLOAD_FILE_TYPE.contains(fileType);
    }
}
