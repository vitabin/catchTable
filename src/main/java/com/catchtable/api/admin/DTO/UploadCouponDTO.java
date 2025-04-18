package com.catchtable.api.admin.DTO;

import com.catchtable.api.file.domain.FileType;
import jakarta.validation.constraints.Null;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@RequiredArgsConstructor
public class UploadCouponDTO {

    private final MultipartFile file;

    @Null
    private FileType fileType;

    @Null
    private String filename;

    public UploadCouponParam toPrams(String token) {
        return new UploadCouponParam(token, file, filename, fileType);
    }

    public void validate() {
        filename = file.getOriginalFilename();

//      TODO: custom exception으로 변경
        if (filename == null) {
            throw new RuntimeException("Filename can be null");
        }

        String extension = filename.substring(filename.lastIndexOf('.'))
                                   .toLowerCase();
        fileType = FileType.getFileType(extension);

//      TODO: custom exception으로 변경
        if (fileType != FileType.CSV && fileType != FileType.XLS && fileType != FileType.XLSX) {
            throw new RuntimeException("Unsupported file extension: " + extension);
        }
    }
}
