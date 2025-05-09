package com.catchtable.config;

import java.net.URI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Slf4j
@Configuration
public class S3Config {

    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;

    @Value("${cloud.aws.region}")
    private String region;

    @Value("${cloud.aws.endpoint}")
    private String endpoint;

    @Bean
    public StaticCredentialsProvider awsCredentialsProvider() {
        return StaticCredentialsProvider.create(
            AwsBasicCredentials.create(accessKey, secretKey)
        );
    }

    @Bean
    public S3Client s3Client(StaticCredentialsProvider awsCredentialsProvider) {
        return S3Client.builder()
                       .endpointOverride(URI.create(endpoint))
                       .credentialsProvider(awsCredentialsProvider)
                       .region(Region.of(region))
                       .build();
    }

    @Bean
    public S3Presigner s3Presigner(StaticCredentialsProvider awsCredentialsProvider) {
        return S3Presigner.builder()
                          .endpointOverride(URI.create(endpoint))
                          .region(Region.of(region))
                          .credentialsProvider(awsCredentialsProvider)
                          .build();
    }
}
