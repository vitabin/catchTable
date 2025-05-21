package com.catchtable.api.file.DTO;

import java.net.URL;
import lombok.Getter;

@Getter
public class PreSignedUrlResponse {

    private String bucket;

    private String objectKey;

    private URL preSignedUrl;

    public static PreSignedUrlResponse of(String bucket, String objectKey, URL preSignedUrl) {
        PreSignedUrlResponse response = new PreSignedUrlResponse();
        response.bucket = bucket;
        response.objectKey = objectKey;
        response.preSignedUrl = preSignedUrl;
        return response;
    }
}
