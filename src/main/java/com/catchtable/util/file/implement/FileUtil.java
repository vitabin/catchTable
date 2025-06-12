package com.catchtable.util.file.implement;

import com.catchtable.api.file.domain.FileCategory;
import com.catchtable.api.file.domain.FileType;
import com.catchtable.exception.exception.FileException;
import com.catchtable.response.error.FileErrorCode;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class FileUtil {

    public String getRelativePath(FileCategory fileCategory, FileType fileType) {
        return String.format("%s/%s.%s", fileCategory.toString()
                                                     .toLowerCase(), UUID.randomUUID(),
            fileType.getExtension());
    }

    public String getUUID(String str) {
        if (str == null || !str.contains("/") || !str.contains(".")) {
            throw new FileException(FileErrorCode.INVALID_OBJECT_KEY, str);
        }
        return str.substring(
            str.lastIndexOf("/") + 1, str.lastIndexOf("."));
    }
}
