package com.catchtable.api.admin.DTO;

import com.catchtable.api.file.domain.FileType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class UploadCouponDTO {

    private MultipartFile file;

    public UploadCouponParam toPrams(String token, String filename, FileType fileType) {
        return new UploadCouponParam(token, file, filename, fileType);
    }
}
