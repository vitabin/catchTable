package com.catchtable.app.api.file.DTO;

import com.catchtable.app.api.file.domain.FileCategory;
import lombok.Builder;

@Builder
public record PreSignedUrlRequestParam(String filename, String username, FileCategory category,
                                       String contentType, Long contentLength) {

}
