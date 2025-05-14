package com.catchtable.api.file.Controller;

import com.catchtable.annotation.UserName;
import com.catchtable.api.file.DTO.PreSignedUrlRequestParam;
import com.catchtable.api.file.DTO.PreSignedUrlResponse;
import com.catchtable.api.file.DTO.UploadCheckRequestDTO;
import com.catchtable.api.file.domain.FileCategory;
import com.catchtable.api.file.service.FileService;
import com.catchtable.base.BaseResponse;
import com.catchtable.response.ErrorResponse;
import com.catchtable.response.SuccessResponse;
import com.catchtable.response.error.FileErrorCode;
import com.catchtable.response.success.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @GetMapping("/s3/upload")
    public SuccessResponse<PreSignedUrlResponse> getPreSignedUrl(
        @UserName String username,
        @RequestParam String filename,
        @RequestParam FileCategory category,
        @RequestHeader("Content-Type") String contentType,
        @RequestHeader("Content-Length") Long contentLength) {
        PreSignedUrlRequestParam preSignedUrlRequestParam = new PreSignedUrlRequestParam(filename,
            username, category, contentType, contentLength);
        return SuccessResponse.of(SuccessCode.SUCCESS,
            fileService.getUploadPreSignedUrl(preSignedUrlRequestParam));
    }

    @PutMapping("/s3/upload")
    public BaseResponse<Object> isUploaded(@RequestBody UploadCheckRequestDTO request) {
        if (fileService.isUploaded(request.getObjectKey())) {
            return SuccessResponse.of(SuccessCode.WITHOUT_CONTENT);
        } else {
            return ErrorResponse.of(FileErrorCode.SAVE_FAIL);
        }
    }

    @GetMapping("/s3/download")
    public SuccessResponse<PreSignedUrlResponse> getPreSignedUrl(
        @RequestParam("objectKey") String objectKey) {
        return SuccessResponse.of(SuccessCode.SUCCESS,
            fileService.getDownloadPreSignedUrl(objectKey));
    }
}
