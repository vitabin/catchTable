package com.catchtable.dynamo.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbImmutable;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbImmutable(builder = InvertedIndex.InvertedIndexBuilder.class)
@Getter
@Builder
public class InvertedIndex {

    @Getter(value = AccessLevel.NONE)
    private String indexName;

    private Integer segmentId;

    private Long expirationAt;

    @DynamoDbPartitionKey
    public String getIndexName() {
        return this.indexName;
    }
}
