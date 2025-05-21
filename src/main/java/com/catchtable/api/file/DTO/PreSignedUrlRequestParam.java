package com.catchtable.api.file.DTO;

import com.catchtable.api.file.domain.FileCategory;
import lombok.Builder;

@Builder
public record PreSignedUrlRequestParam(String filename, String username, FileCategory category,
                                       String contentType, Long contentLength) {

}
