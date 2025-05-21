package com.catchtable.api.file.DTO;

import com.catchtable.api.file.domain.FileCategory;
import com.catchtable.api.file.domain.FileType;
import lombok.Getter;

@Getter
public class S3UploadCacheDTO {

    private String contentType;
    private long contentLength;
    private String username;
    private FileType fileType;
    private String filename;
    private FileCategory fileCategory;

    public static S3UploadCacheDTO of(PreSignedUrlRequestParam preSignedUrlRequestParam) {
        S3UploadCacheDTO dto = new S3UploadCacheDTO();
        dto.contentLength = preSignedUrlRequestParam.contentLength();
        dto.filename = preSignedUrlRequestParam.filename();
        dto.username = preSignedUrlRequestParam.username();
        dto.contentType = preSignedUrlRequestParam.contentType();
        dto.fileType = FileType.getFileType(dto.filename);
        dto.fileCategory = preSignedUrlRequestParam.category();
        return dto;
    }
}
