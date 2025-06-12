package com.catchtable.dynamo.repository;

import com.catchtable.dynamo.DTO.DynamoTable;
import com.catchtable.dynamo.domain.UserAchievement;
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
public class UserAchievementRepository {

    private final DynamoDbTable<UserAchievement> table;
    private final Logger logger;

    UserAchievementRepository(DynamoDbEnhancedClient client) {
        table = client.table(DynamoTable.USER_ACHIEVEMENT.getTableName(),
            TableSchema.fromBean(UserAchievement.class));
        logger = LoggerFactory.getLogger(this.getClass());
    }

    private void putItem(UserAchievement item) {
        PutItemEnhancedResponse<UserAchievement> response = table.putItemWithResponse(
            PutItemEnhancedRequest.<UserAchievement>builder(UserAchievement.class)
                                  .item(item)
                                  .returnConsumedCapacity(ReturnConsumedCapacity.TOTAL)
                                  .build());
        logger.info("PutItem call consumed [{}] Write Capacity Unites (WCU)",
            response.consumedCapacity()
                    .capacityUnits());
    }

    public Optional<UserAchievement> getItem(Integer id) {
        GetItemEnhancedResponse<UserAchievement> response = table.getItemWithResponse(
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

    private void getInfo(GetItemEnhancedResponse<UserAchievement> response) {
        logger.info("GetItem call consumed [{}] Write Capacity Unites (WCU)",
            response.consumedCapacity()
                    .capacityUnits());
    }
}
