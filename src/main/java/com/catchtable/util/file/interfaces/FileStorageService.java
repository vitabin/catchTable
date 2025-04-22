package com.catchtable.util.file.interfaces;

import java.io.InputStream;
import java.nio.file.Path;

public interface FileStorageService {

    void saveFile(InputStream inputStream, Path path);

    void deleteFile(Path path);

    InputStream loadFile(Path path);
}
