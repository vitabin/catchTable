package com.catchtable.util.s3;

import java.time.Duration;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

@Getter
@Component
public class S3Util {

    @Value("${cloud.s3.bucket}")
    private String bucket;

    @Value("${cloud.s3.expiration}")
    private int expiration;

    // Put --------------------------------------------------------------------------------
    public PutObjectRequest createPutObjectRequest(String objectKey, Long contentLength,
        String contentType) {
        return PutObjectRequest.builder()
                               .bucket(bucket)
                               .contentLength(contentLength)
                               .contentType(contentType)
                               .key(objectKey)
                               .build();
    }

    public PutObjectPresignRequest createPutObjectPresignRequest(String objectKey,
        Long contentLength, String contentType) {
        PutObjectRequest putObjectRequest = createPutObjectRequest(objectKey, contentLength,
            contentType);
        return PutObjectPresignRequest.builder()
                                      .signatureDuration(Duration.ofMinutes(expiration))
                                      .putObjectRequest(putObjectRequest)
                                      .build();
    }

    // Get --------------------------------------------------------------------------------
    public GetObjectRequest createGetObjectRequest(String objectKey, String contentType) {
        return GetObjectRequest.builder()
                               .bucket(bucket)
                               .key(objectKey)
                               .responseContentType(contentType)
                               .build();
    }

    public GetObjectPresignRequest createGetObjectPresignRequest(String objectKey,
        String contentType) {
        GetObjectRequest getObjectRequest = createGetObjectRequest(objectKey, contentType);
        return GetObjectPresignRequest.builder()
                                      .signatureDuration(Duration.ofMinutes(expiration))
                                      .getObjectRequest(getObjectRequest)
                                      .build();
    }

    public HeadObjectRequest createHeadObjectRequest(String objectKey) {
        return HeadObjectRequest.builder()
                                .bucket(bucket)
                                .key(objectKey)
                                .build();
    }
}
