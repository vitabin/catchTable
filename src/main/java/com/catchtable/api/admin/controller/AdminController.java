package com.catchtable.api.admin.controller;

import com.catchtable.api.admin.DTO.UploadCouponDTO;
import com.catchtable.api.admin.service.AdminService;
import com.catchtable.api.file.domain.FileType;
import com.catchtable.exception.exception.FileException;
import com.catchtable.response.SuccessResponse;
import com.catchtable.response.error.FileErrorCode;
import com.catchtable.response.success.SuccessCode;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/coupons")
    public SuccessResponse<Object> uploadCoupons(@RequestHeader("Authorization") String token,
        @ModelAttribute UploadCouponDTO uploadCouponDTO) {
        String filename = uploadCouponDTO.getFile()
                                         .getOriginalFilename();

        if (filename == null) {
            throw new FileException(FileErrorCode.NULL_FILE_NAME);
        }

        String extension = filename.substring(filename.lastIndexOf('.'))
                                   .toLowerCase();
        FileType fileType = FileType.getFileType(extension);

        if (!FileType.isCouponExtension(fileType)) {
            throw new FileException(FileErrorCode.UNSUPPORTED_FILE_EXTENSION);
        }
        adminService.uploadCoupon(uploadCouponDTO.toPrams(token, filename, fileType));
        return SuccessResponse.of(SuccessCode.WITHOUT_CONTENT);
    }

    @DeleteMapping("/coupons/{id}")
    private SuccessResponse<Object> deleteCoupons(@RequestHeader("Authorization") String token,
        @PathVariable Long id) {
        adminService.deleteCoupon(id);
        return SuccessResponse.of(SuccessCode.WITHOUT_CONTENT);
    }

    @GetMapping("/coupons/{id}/sample")
    private Resource downloadSampleCoupon(@RequestHeader("Authorization") String token,
        @PathVariable Long id, @RequestParam("nums") Integer nums, HttpServletResponse response) {
        Resource resource = adminService.downloadSampleCoupon(id, nums);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename=\"" + resource.getFilename() + "\"");
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);

        return resource;
    }

    @GetMapping("/coupons/{id}")
    public Resource downloadCouponFile(@PathVariable("id") Long id, HttpServletResponse response) {
        Resource resource = adminService.downloadCoupon(id);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
            "attachment; filename=\"" + resource.getFilename() + "\"");
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);

        return resource;
    }
}
