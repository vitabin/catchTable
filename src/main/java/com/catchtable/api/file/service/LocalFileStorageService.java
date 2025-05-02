package com.catchtable.api.file.service;

import com.catchtable.exception.exception.FileException;
import com.catchtable.response.error.FileErrorCode;
import com.catchtable.util.file.interfaces.FileStorageService;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class LocalFileStorageService implements FileStorageService {

    @Override
    public void saveFile(MultipartFile file, Path path) {
        try {
            Path directory = path.getParent();
            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
            }
            file.transferTo(path);
        } catch (IOException e) {
            deleteFile(path);
            throw new FileException(FileErrorCode.SAVE_FAIL, path);
        }
    }

    @Override
    public void deleteFile(Path path) {
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new FileException(FileErrorCode.DELETE_FAIL, path);
        }
    }

    @Override
    public InputStream loadFile(Path path) {
        try {
            return Files.newInputStream(path);
        } catch (IOException e) {
            throw new FileException(FileErrorCode.LOAD_FAIL, path);
        }
    }
}
