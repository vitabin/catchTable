package com.catchtable.app.api.file.DTO;

import com.catchtable.app.api.file.domain.FileCategory;
import com.catchtable.app.api.file.domain.FileType;
import com.catchtable.app.api.user.domain.UserEntity;
import java.util.UUID;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

@Builder
public record UploadFileParam(FileType fileType, String filename, UserEntity userEntity,
                              FileCategory category, MultipartFile multipartFile) {

    public String getRelativePath() {
        return String.format("%s/%s.%s", category.toString()
                                                 .toLowerCase(), UUID.randomUUID(),
            fileType.getExtension());
    }
}
