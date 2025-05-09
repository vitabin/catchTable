package com.catchtable.api.file.service;

import com.catchtable.api.file.DTO.PreSignedUrlResponse;
import com.catchtable.util.s3.S3Util;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
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
    private final S3Util s3Util;
    private final StringRedisTemplate redisTemplate;

    public PreSignedUrlResponse generatePutPreSignedURL(String objectKey) {
        PutObjectPresignRequest putObjectPresignRequest = s3Util.createPutObjectPresignRequest(objectKey);
        PresignedPutObjectRequest preSignedRequest = s3Presigner.presignPutObject(putObjectPresignRequest);

        return PreSignedUrlResponse.of(s3Util.getBucket(), objectKey, preSignedRequest.url());
    }

    public PreSignedUrlResponse generateGetPreSignedURL(String objectKey) {
        GetObjectPresignRequest getObjectPresignRequest = s3Util.createGetObjectPresignRequest(objectKey);
        PresignedGetObjectRequest preSignedRequest = s3Presigner.presignGetObject(getObjectPresignRequest);

        return PreSignedUrlResponse.of(s3Util.getBucket(), objectKey, preSignedRequest.url());
    }
}
