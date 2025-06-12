package com.catchtable.dynamo.repository;

import com.catchtable.dynamo.DTO.DynamoTable;
import com.catchtable.dynamo.domain.UserSegment;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.GetItemEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.GetItemEnhancedResponse;
import software.amazon.awssdk.enhanced.dynamodb.model.PutItemEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.PutItemEnhancedResponse;
import software.amazon.awssdk.services.dynamodb.model.ReturnConsumedCapacity;

@Component
public class UserSegmentRepository {

    private final DynamoDbTable<UserSegment> table;
    private final Logger logger;

    UserSegmentRepository(DynamoDbEnhancedClient client) {
        table = client.table(DynamoTable.USER_SEGMENT.getTableName(),
            TableSchema.fromBean(UserSegment.class));
        logger = LoggerFactory.getLogger(this.getClass());
    }

    private void putItem(UserSegment item) {
        PutItemEnhancedResponse<UserSegment> response = table.putItemWithResponse(
            PutItemEnhancedRequest.<UserSegment>builder(UserSegment.class)
                                  .item(item)
                                  .returnConsumedCapacity(ReturnConsumedCapacity.TOTAL)
                                  .build());
        logger.info("PutItem call consumed [{}] Write Capacity Unites (WCU)",
            response.consumedCapacity()
                    .capacityUnits());
    }

    public Optional<UserSegment> getItem(Integer id) {
        GetItemEnhancedResponse<UserSegment> response = table.getItemWithResponse(
            GetItemEnhancedRequest.builder()
                                  .key(Key.builder()
                                          .partitionValue(id)
                                          .build())
                                  .returnConsumedCapacity(ReturnConsumedCapacity.TOTAL)
                                  .build()
        );
        getInfo(response);
        return Optional.of(response.attributes());
    }

    public Optional<UserSegment> getItem(Integer pk, Integer sk) {
        GetItemEnhancedResponse<UserSegment> response = table.getItemWithResponse(
            GetItemEnhancedRequest.builder()
                                  .key(Key.builder()
                                          .partitionValue(pk)
                                          .sortValue(sk)
                                          .build())
                                  .returnConsumedCapacity(ReturnConsumedCapacity.TOTAL)
                                  .build()
        );
        getInfo(response);
        return Optional.of(response.attributes());
    }

    private void getInfo(GetItemEnhancedResponse<UserSegment> response) {
        logger.info("GetItem call consumed [{}] Write Capacity Unites (WCU)",
            response.consumedCapacity()
                    .capacityUnits());
    }
}
