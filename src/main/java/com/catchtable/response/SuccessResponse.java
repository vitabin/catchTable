package com.catchtable.response;

import com.catchtable.base.BaseCode;
import com.catchtable.base.BaseResponse;
import com.catchtable.response.interfaces.ResponseMetaData;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.jsonwebtoken.lang.Assert;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@SuperBuilder
@NoArgsConstructor
public class SuccessResponse<T> extends BaseResponse<T> {

    private T body;
    private Boolean hasNext;
    private Integer nextCursor;
    private Integer items;

    public static <T> SuccessResponse<T> of(BaseCode code, T body) {
        return SuccessResponse.<T>builder()
                              .message(code.getMessage())
                              .status(code.getStatus())
                              .timestamp(LocalDateTime.now())
                              .body(body)
                              .build();
    }

    public static SuccessResponse<Object> of(BaseCode code) {
        return SuccessResponse.builder()
                              .message(code.getMessage())
                              .status(code.getStatus())
                              .timestamp(LocalDateTime.now())
                              .build();
    }

    public static <T> SuccessResponse<T> of(BaseCode code, T body,
        ResponseMetaData responseMetaData) {
        Assert.notNull(responseMetaData.getHasNext(), "Response 'hasNext' value is null");
        Assert.notNull(responseMetaData.getNextCursor(), "Response 'nextCursor' value is null");
        Assert.notNull(responseMetaData.getItems(), "Response 'items' value is null");
        return SuccessResponse.<T>builder()
                              .body(body)
                              .message(code.getMessage())
                              .status(code.getStatus())
                              .items(responseMetaData.getItems())
                              .hasNext(responseMetaData.getHasNext())
                              .nextCursor(responseMetaData.getNextCursor())
                              .timestamp(LocalDateTime.now())
                              .build();
    }
}
