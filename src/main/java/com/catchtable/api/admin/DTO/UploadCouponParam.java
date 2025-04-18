package com.catchtable.api.admin.DTO;

import com.catchtable.api.file.domain.FileType;
import org.springframework.web.multipart.MultipartFile;

public record UploadCouponParam(String token, MultipartFile file, String filename, FileType type) {

}
