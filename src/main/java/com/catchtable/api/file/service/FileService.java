package com.catchtable.api.file.service;

import com.catchtable.api.file.DTO.PreSignedUrlRequestParam;
import com.catchtable.api.file.DTO.PreSignedUrlResponse;
import com.catchtable.api.file.DTO.S3UploadCacheDTO;
import com.catchtable.api.file.DTO.UploadFileParam;
import com.catchtable.api.file.domain.FileEntity;
import com.catchtable.api.file.domain.FileType;
import com.catchtable.api.file.repository.FileProperties;
import com.catchtable.api.file.repository.FileRepository;
import com.catchtable.api.user.domain.UserEntity;
import com.catchtable.api.user.repository.UserRepository;
import com.catchtable.exception.exception.FileException;
import com.catchtable.exception.exception.UserException;
import com.catchtable.redis.RedisClient;
import com.catchtable.response.error.FileErrorCode;
import com.catchtable.response.error.UserErrorCode;
import com.catchtable.util.file.classes.FileUtil;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;
    private final FileProperties fileProperties;
    private final LocalFileStorageService localFileStorageService;
    private final S3Service s3Service;
    private final UserRepository userRepository;
    private final RedisClient redisHelper;
    private final FileUtil fileUtil;

    @Transactional
    public void uploadFileOfLocalStorage(UploadFileParam uploadFileParam) {
        String relativePath = uploadFileParam.getRelativePath();
        String uuid = fileUtil.getUUID(relativePath);
        Path fullPath = Path.of(fileProperties.getPreFixPath(), relativePath);

        localFileStorageService.saveFile(uploadFileParam.multipartFile(), fullPath);

        fileRepository.save(FileEntity.of(uploadFileParam, uuid, fullPath.toString()));
    }

    public FileEntity getFileOfLocalStorage(Long id) {
        return fileRepository.findById(id)
                             .orElseThrow(() -> new FileException(FileErrorCode.FILE_NOT_FOUND));
    }

    public Resource downloadFileOfLocalStorage(Long id) {
        FileEntity fileEntity = fileRepository.findById(id)
                                              .orElseThrow(() -> new FileException(
                                                  FileErrorCode.FILE_NOT_FOUND));
        Path filePath = Path.of(fileProperties.getPreFixPath(), fileEntity.getPath());

        try {
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists()) {
                throw new FileException(FileErrorCode.FILE_NOT_FOUND, filePath);
            }

            return resource;
        } catch (MalformedURLException e) {
            throw new FileException(FileErrorCode.INVALID_FILE_PATH, filePath);
        }
    }

    public void deleteFileOfLocalStorage(Long id) {
        FileEntity fileEntity = fileRepository.findById(id)
                                              .orElseThrow(() -> new FileException(
                                                  FileErrorCode.FILE_NOT_FOUND));
        fileEntity.delete();
        fileRepository.save(fileEntity);
    }

    @Transactional
    public PreSignedUrlResponse getUploadPreSignedUrl(
        PreSignedUrlRequestParam preSignedUrlRequestParam) {
        String filename = preSignedUrlRequestParam.filename();
        FileType fileType = FileType.getFileType(filename);
        String objectKey = fileUtil.getRelativePath(preSignedUrlRequestParam.category(), fileType);
        redisHelper.set(objectKey, S3UploadCacheDTO.of(preSignedUrlRequestParam),
            Duration.ofSeconds(300));

        return s3Service.generatePutPreSignedURL(objectKey,
            preSignedUrlRequestParam.contentLength(), preSignedUrlRequestParam.contentType());
    }

    public PreSignedUrlResponse getDownloadPreSignedUrl(String objectKey) {
        String uuid = fileUtil.getUUID(objectKey);
        FileEntity fileEntity = fileRepository.findByUuid(uuid)
                                              .orElseThrow(() -> new FileException(
                                                  FileErrorCode.FILE_NOT_FOUND));
        return s3Service.generateGetPreSignedURL(objectKey, fileEntity.getMimeType());
    }

    public Boolean isUploaded(String objectKey) {
        S3UploadCacheDTO cache = redisHelper.get(objectKey, S3UploadCacheDTO.class);

        if (cache == null || !s3Service.isUploaded(objectKey, cache.getContentType(),
            cache.getContentLength())) {
            return false;
        }

        UserEntity userEntity = userRepository.findByUserName(cache.getUsername())
                                              .orElseThrow(() -> new UserException(
                                                  UserErrorCode.USER_NOT_FOUND));
        fileRepository.save(
            FileEntity.of(cache, userEntity, fileUtil.getUUID(objectKey), objectKey));
        redisHelper.delete(objectKey);

        return true;
    }
}
