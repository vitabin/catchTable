package com.catchtable.util.file.interfaces;

import java.io.InputStream;
import java.nio.file.Path;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

    void saveFile(MultipartFile file, Path path);

    void deleteFile(Path path);

    InputStream loadFile(Path path);
}
