package com.catchtable.api.file.service;

import com.catchtable.api.file.DTO.PreSignedUrlResponse;
import com.catchtable.util.s3.S3Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadObjectResponse;
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

    public PreSignedUrlResponse generatePutPreSignedURL(String objectKey, Long contentLength,
        String contentType) {
        PutObjectPresignRequest putObjectPresignRequest = s3Util.createPutObjectPresignRequest(
            objectKey, contentLength, contentType);
        PresignedPutObjectRequest preSignedRequest = s3Presigner.presignPutObject(
            putObjectPresignRequest);

        return PreSignedUrlResponse.of(s3Util.getBucket(), objectKey, preSignedRequest.url());
    }

    public PreSignedUrlResponse generateGetPreSignedURL(String objectKey, String contentType) {
        GetObjectPresignRequest getObjectPresignRequest = s3Util.createGetObjectPresignRequest(
            objectKey, contentType);
        PresignedGetObjectRequest preSignedRequest = s3Presigner.presignGetObject(
            getObjectPresignRequest);

        return PreSignedUrlResponse.of(s3Util.getBucket(), objectKey, preSignedRequest.url());
    }

    public Boolean isUploaded(String objectKey, String contentType, Long contentLength) {
        try {
            HeadObjectRequest headObjectRequest = s3Util.createHeadObjectRequest(objectKey);
            HeadObjectResponse headObject = s3Client.headObject(headObjectRequest);
            Long fileSize = headObject.contentLength();
            String type = headObject.contentType();

            if (fileSize == null || fileSize == 0 || !fileSize.equals(contentLength)) {
                return false;
            }

            if (!type.equals(contentType)) {
                return false;
            }
            return true;
        } catch (AwsServiceException e) {
            return false;
        }
    }
}
