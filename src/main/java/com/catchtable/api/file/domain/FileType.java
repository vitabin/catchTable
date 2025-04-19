package com.catchtable.api.file.domain;

import java.util.HashSet;
import java.util.Set;
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

    public static final Set<String> COUPON_EXTENSIONS = new HashSet<>();

    static {
        COUPON_EXTENSIONS.add(CSV.getExtension());
        COUPON_EXTENSIONS.add(XLS.getExtension());
        COUPON_EXTENSIONS.add(XLSX.getExtension());
    }

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

    public static boolean isCouponExtension(String ce) {
        return COUPON_EXTENSIONS.contains(ce);
    }
}
