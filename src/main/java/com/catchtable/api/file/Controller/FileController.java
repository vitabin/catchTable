package com.catchtable.api.file.Controller;

import com.catchtable.annotation.UserName;
import com.catchtable.api.file.DTO.PreSignedUrlRequestDTO;
import com.catchtable.api.file.DTO.PreSignedUrlRequestParam;
import com.catchtable.api.file.DTO.PreSignedUrlResponse;
import com.catchtable.api.file.service.FileService;
import com.catchtable.response.SuccessResponse;
import com.catchtable.response.success.SuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping("/s3")
    public SuccessResponse<PreSignedUrlResponse> getPreSignedUrl(
        @UserName String username,
        @RequestBody PreSignedUrlRequestDTO preSignedUrlRequestDTO) {
        PreSignedUrlRequestParam param = PreSignedUrlRequestParam.builder()
                                                                 .username(username)
                                                                 .filename(preSignedUrlRequestDTO.getFilename())
                                                                 .category(preSignedUrlRequestDTO.getCategory())
                                                                 .build();
        return SuccessResponse.of(SuccessCode.SUCCESS, fileService.getUploadPreSignedUrl(param));
    }

    @PostMapping("/s3")
    public void validateFile(@RequestBody String objectKey) {

    }

    @GetMapping("/s3")
    public SuccessResponse<PreSignedUrlResponse> getPreSignedUrl(
        @RequestParam("objectKey") String objectKey) {
        return SuccessResponse.of(SuccessCode.SUCCESS, fileService.getDownloadPreSignedUrl(objectKey));
    }
}
