package com.catchtable.api.admin.DTO;

import com.catchtable.api.file.domain.FileType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@RequiredArgsConstructor
public class UploadCouponDTO {

    private final MultipartFile file;

    public UploadCouponParam toPrams(String token, String filename, FileType fileType) {
        return new UploadCouponParam(token, file, filename, fileType);
    }
}
