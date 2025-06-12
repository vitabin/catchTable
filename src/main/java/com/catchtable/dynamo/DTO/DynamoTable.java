package com.catchtable.dynamo.DTO;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DynamoTable {
    USER_ACHIEVEMENT("user_achievement"),
    USER_SEGMENT("user_segment"),
    ;

    private final String tableName;
}
