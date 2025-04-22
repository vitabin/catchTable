package com.catchtable.api.file.service;

import com.catchtable.api.file.DTO.UploadFileParam;
import com.catchtable.api.file.domain.FileEntity;
import com.catchtable.api.file.repository.FileProperties;
import com.catchtable.api.file.repository.FileRepository;
import java.io.IOException;
import java.io.InputStream;
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

    public FileEntity uploadFile(UploadFileParam uploadFileParam) {
        String relativePath = uploadFileParam.getRelativePath();
        String uuid = relativePath.substring(
            relativePath.lastIndexOf("/") + 1, relativePath.lastIndexOf("."));
        Path fullPath = Path.of(fileProperties.getPreFixPath(), relativePath);

        try (InputStream inputStream = uploadFileParam.multipartFile()
                                                      .getInputStream()) {
            localFileStorageService.saveFile(inputStream, fullPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return fileRepository.save(FileEntity.create(uploadFileParam, uuid, relativePath));
    }

    public FileEntity getFile(Long id) {
        return fileRepository.findById(id)
                             .orElseThrow(() -> new RuntimeException("File Not Found"));
    }

    public Resource downloadFile(Long id) {
        FileEntity fileEntity = fileRepository.findById(id)
                                              .orElseThrow(() -> new RuntimeException("File Not Found"));
        Path filePath = Path.of(fileProperties.getPreFixPath(), fileEntity.getPath());

        try {
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists()) {
                throw new RuntimeException("File not found");
            }

            return resource;
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid file path", e);
        }
    }

    public void deleteFile(Long id) {
        FileEntity fileEntity = fileRepository.findById(id)
                                              .orElseThrow(() -> new RuntimeException("File Not Found"));
        fileEntity.delete();
        fileRepository.save(fileEntity);
    }
}
