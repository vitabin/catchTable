package com.catchtable.app.api.admin.DTO;

import com.catchtable.app.api.file.domain.FileType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class UploadCouponDTO {

    private MultipartFile file;

    public UploadCouponParam toPrams(String username, String filename, FileType fileType) {
        return new UploadCouponParam(username, file, filename, fileType);
    }
}
