package com.catchtable.api.file.service;

import com.catchtable.api.file.DTO.UploadFileParam;
import com.catchtable.api.file.domain.FileEntity;
import com.catchtable.api.file.repository.FileProperties;
import com.catchtable.api.file.repository.FileRepository;
import com.catchtable.exception.exception.FileException;
import com.catchtable.response.error.FileErrorCode;
import java.net.MalformedURLException;
import java.nio.file.Path;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;
    private final FileProperties fileProperties;
    private final LocalFileStorageService localFileStorageService;

    public void uploadFile(UploadFileParam uploadFileParam) {
        String relativePath = uploadFileParam.getRelativePath();
        String uuid = relativePath.substring(
            relativePath.lastIndexOf("/") + 1, relativePath.lastIndexOf("."));
        Path fullPath = Path.of(fileProperties.getPreFixPath(), relativePath);

        localFileStorageService.saveFile(uploadFileParam.multipartFile(), fullPath);

        fileRepository.save(FileEntity.create(uploadFileParam, uuid, relativePath));
    }

    public FileEntity getFile(Long id) {
        return fileRepository.findById(id)
                             .orElseThrow(() -> new FileException(FileErrorCode.FILE_NOT_FOUND));
    }

    public Resource downloadFile(Long id) {
        FileEntity fileEntity = fileRepository.findById(id)
                                              .orElseThrow(() -> new FileException(FileErrorCode.FILE_NOT_FOUND));
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

    public void deleteFile(Long id) {
        FileEntity fileEntity = fileRepository.findById(id)
                                              .orElseThrow(() -> new FileException(FileErrorCode.FILE_NOT_FOUND));
        fileEntity.delete();
        fileRepository.save(fileEntity);
    }
}
