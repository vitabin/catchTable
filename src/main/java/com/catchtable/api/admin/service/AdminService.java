package com.catchtable.api.admin.service;

import com.catchtable.api.admin.DTO.UploadCouponParam;
import com.catchtable.api.file.DTO.UploadFileParam;
import com.catchtable.api.file.domain.FileEntity;
import com.catchtable.api.file.domain.FileType;
import com.catchtable.api.file.domain.SavePath;
import com.catchtable.api.file.repository.FileProperties;
import com.catchtable.api.file.service.FileService;
import com.catchtable.api.user.domain.UserEntity;
import com.catchtable.api.user.repository.UserRepository;
import com.catchtable.exception.exception.FileException;
import com.catchtable.exception.exception.UserException;
import com.catchtable.response.error.FileErrorCode;
import com.catchtable.response.error.UserErrorCode;
import com.catchtable.util.file.interfaces.CouponFileParser;
import com.catchtable.util.jwt.JwtUtil;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final FileService fileService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final FileProperties fileProperties;
    private final Map<String, CouponFileParser> couponParserMap;

    //  TODO: response 변경
    public void uploadCoupon(UploadCouponParam uploadCouponParam) {
        FileType fileType = uploadCouponParam.type();
        CouponFileParser parser = couponParserMap.get(fileType.getMimeType());

        parser.validate(uploadCouponParam);

        String username = jwtUtil.getUserName(jwtUtil.resolveToken(uploadCouponParam.token()));
        UserEntity userEntity = userRepository.findByUserName(username)
                                              .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

        UploadFileParam uploadFileParam = UploadFileParam.builder()
                                                         .multipartFile(uploadCouponParam.file())
                                                         .filename(uploadCouponParam.filename())
                                                         .savePath(SavePath.COUPON.getPath())
                                                         .fileType(uploadCouponParam.type())
                                                         .userEntity(userEntity)
                                                         .build();

        fileService.uploadFile(uploadFileParam);
    }

    public Resource downloadCoupon(Long id) {
        return fileService.downloadFile(id);
    }

    public Resource downloadSampleCoupon(Long id, Integer nums) {
        FileEntity fileEntity = fileService.getFile(id);
        Path filePath = Path.of(fileProperties.getPreFixPath(), fileEntity.getPath());
        CouponFileParser parser = couponParserMap.get(fileEntity.getFileType()
                                                                .getMimeType());

        Path sampleFilePath = Path.of(fileProperties.getPreFixPath(), fileEntity.getPath()
                                                                                .replace("coupon", "tmp"));

        try (BufferedWriter writer = Files.newBufferedWriter(sampleFilePath, StandardCharsets.UTF_8);
            InputStream inputStream = new FileInputStream(filePath.toFile())) {
            List<String> rows = parser.getRow(inputStream, nums);

            for (String row : rows) {
                writer.write(row);
            }

            return new UrlResource(sampleFilePath.toUri());
        } catch (IOException e) {
            throw new FileException(FileErrorCode.IO_EXCEPTION);
        }
    }

    public void deleteCoupon(Long id) {
        fileService.deleteFile(id);
    }
}
