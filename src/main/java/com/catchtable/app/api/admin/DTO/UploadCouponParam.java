package com.catchtable.app.api.admin.DTO;

import com.catchtable.app.api.file.domain.FileType;
import org.springframework.web.multipart.MultipartFile;

public record UploadCouponParam(String username, MultipartFile file, String filename,
                                FileType type) {

}
