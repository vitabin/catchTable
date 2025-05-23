package com.catchtable.app.api.admin.service;

import com.catchtable.app.api.admin.DTO.UploadCouponParam;
import com.catchtable.app.api.file.DTO.UploadFileParam;
import com.catchtable.app.api.file.domain.FileCategory;
import com.catchtable.app.api.file.domain.FileEntity;
import com.catchtable.app.api.file.domain.FileType;
import com.catchtable.app.api.file.repository.FileProperties;
import com.catchtable.app.api.file.service.FileService;
import com.catchtable.app.api.user.domain.UserEntity;
import com.catchtable.app.api.user.repository.UserRepository;
import com.catchtable.exception.exception.FileException;
import com.catchtable.exception.exception.UserException;
import com.catchtable.response.error.FileErrorCode;
import com.catchtable.response.error.UserErrorCode;
import com.catchtable.util.file.interfaces.CouponFileParser;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final FileService fileService;
    private final UserRepository userRepository;
    private final FileProperties fileProperties;
    private final Map<FileType, CouponFileParser> couponParserMap;

    public AdminService(FileService fileService,
        UserRepository userRepository,
        FileProperties fileProperties,
        List<CouponFileParser> couponParsers) {

        this.fileService = fileService;
        this.userRepository = userRepository;
        this.fileProperties = fileProperties;
        this.couponParserMap = couponParsers.stream()
                                            .flatMap(parser -> parser.getSupportedFileTypes()
                                                                     .stream()
                                                                     .map(type -> Map.entry(type,
                                                                         parser)))
                                            .collect(Collectors.toMap(Map.Entry::getKey,
                                                Map.Entry::getValue));
    }

    public void uploadCoupon(UploadCouponParam uploadCouponParam) {
        FileType fileType = uploadCouponParam.type();
        CouponFileParser parser = couponParserMap.get(fileType);

        parser.validate(uploadCouponParam);

        UserEntity userEntity = userRepository.findByUserName(uploadCouponParam.username())
                                              .orElseThrow(() -> new UserException(
                                                  UserErrorCode.USER_NOT_FOUND));

        UploadFileParam uploadFileParam = UploadFileParam.builder()
                                                         .multipartFile(uploadCouponParam.file())
                                                         .filename(uploadCouponParam.filename())
                                                         .category(FileCategory.COUPON)
                                                         .fileType(fileType)
                                                         .userEntity(userEntity)
                                                         .build();

        fileService.uploadFileOfLocalStorage(uploadFileParam);
    }

    public Resource downloadCoupon(Long id) {
        return fileService.downloadFileOfLocalStorage(id);
    }

    public Resource downloadSampleCoupon(Long id, Integer nums) {
        FileEntity fileEntity = fileService.getFileOfLocalStorage(id);
        Path filePath = Path.of(fileProperties.getPreFixPath(), fileEntity.getPath());
        CouponFileParser parser = couponParserMap.get(fileEntity.getFileType());

        Path sampleFilePath = Path.of(fileProperties.getPreFixPath(), fileEntity.getPath()
                                                                                .replace("coupon",
                                                                                    "tmp"));

        try (BufferedWriter writer = Files.newBufferedWriter(sampleFilePath,
            StandardCharsets.UTF_8);
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
        fileService.deleteFileOfLocalStorage(id);
    }
}
