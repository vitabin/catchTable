package com.catchtable.dynamo.domain;

import com.catchtable.api.user.domain.UserGender;
import lombok.Builder;
import lombok.Getter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbImmutable;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@Getter
@Builder
@DynamoDbImmutable(builder = UserAchievement.UserAchievementBuilder.class)
public class UserAchievement {

    @Getter(onMethod_ = @DynamoDbPartitionKey)
    private String userId;

    private UserGender gender;

    private String location;

    private Integer age;

    private Integer lastVisitDate;

    private Integer monthlyVisitDate;

    private Integer purchaseCount;

    private Integer purchaseAmount;

    private Integer purchaseCategoryKorean;

    private Integer purchaseCategoryJapanese;

    private Integer purchaseCategoryItalian;

    private Integer adClickCount;

    private Integer eventParticipationCount;
}
