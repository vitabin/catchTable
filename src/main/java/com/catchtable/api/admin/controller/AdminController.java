package com.catchtable.api.admin.controller;

import com.catchtable.api.admin.DTO.UploadCouponDTO;
import com.catchtable.api.admin.service.AdminService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    // TODO: response 변경
    @PostMapping("/coupons")
    public void uploadCoupons(@RequestHeader("Authorization") String token,
        @RequestParam("file") MultipartFile file) {
        UploadCouponDTO uploadCouponDTO = new UploadCouponDTO(file);
        uploadCouponDTO.validate();
        adminService.uploadCoupon(uploadCouponDTO.toPrams(token));
    }

    @DeleteMapping("/coupons/{id}")
    private void deleteCoupons(@RequestHeader("Authorization") String token,
        @PathVariable Long id) {
        adminService.deleteCoupon(id);
    }

    @GetMapping("/coupons/{id}/sample")
    private Resource downloadSampleCoupon(@RequestHeader("Authorization") String token,
        @PathVariable Long id, @RequestParam("nums") Integer nums) {
        return adminService.downloadSampleCoupon(id, nums);
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
