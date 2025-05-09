package com.catchtable.api.file.service;

import com.catchtable.api.file.DTO.PreSignedUrlResponse;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;

    @Value("${cloud.s3.bucket}")
    private String bucket;

    @Value("${cloud.s3.expiration}")
    private int expiration;

    public PreSignedUrlResponse generatePutPreSignedURL(String objectKey) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                                                            .bucket(bucket)
                                                            .key(objectKey)
                                                            .build();

        PutObjectPresignRequest getObjectPresignRequest =
            PutObjectPresignRequest.builder()
                                   .signatureDuration(Duration.ofMinutes(expiration))
                                   .putObjectRequest(putObjectRequest)
                                   .build();

        PresignedPutObjectRequest preSignedRequest =
            s3Presigner.presignPutObject(getObjectPresignRequest);

        return PreSignedUrlResponse.of(bucket, objectKey, preSignedRequest.url());
    }

    public PreSignedUrlResponse generateGetPreSignedURL(String objectKey) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                                                            .bucket(bucket)
                                                            .key(objectKey)
                                                            .build();

        GetObjectPresignRequest getObjectPresignRequest =
            GetObjectPresignRequest.builder()
                                   .signatureDuration(Duration.ofMinutes(expiration))
                                   .getObjectRequest(getObjectRequest)
                                   .build();

        PresignedGetObjectRequest preSignedRequest =
            s3Presigner.presignGetObject(getObjectPresignRequest);

        return PreSignedUrlResponse.of(bucket, objectKey, preSignedRequest.url());
    }
}
