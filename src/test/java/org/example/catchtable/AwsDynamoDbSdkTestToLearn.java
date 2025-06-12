package org.example.catchtable;

import io.github.cdimascio.dotenv.Dotenv;
import java.net.URI;
import org.junit.jupiter.api.BeforeEach;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

class AwsDynamoDbSdkTestToLearn {

    private final Dotenv env = Dotenv.load();
    private DynamoDbClient dynamoDbClient;

    @BeforeEach
    void setup() {

        StaticCredentialsProvider awsCredentialsProvider = StaticCredentialsProvider.create(
            AwsBasicCredentials.create(env.get("AWS_ACCESS_KEY"), env.get("AWS_SECRET_KEY"))
        );

        String endpoint = "http://awslocal:4566";
        dynamoDbClient = DynamoDbClient.builder()
                                       .credentialsProvider(awsCredentialsProvider)
                                       .endpointOverride(URI.create(endpoint))
                                       .region(Region.of(env.get("AWS_REGION")))
                                       .build();
    }
}
