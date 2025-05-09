package com.catchtable.api.file.DTO;

import com.catchtable.api.file.domain.FileCategory;
import lombok.Getter;

@Getter
public class PreSignedUrlRequestDTO {

    private String filename;
    private FileCategory category;
}
