package com.catchtable.response;

import com.catchtable.base.BaseCode;
import com.catchtable.base.BaseResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@SuperBuilder
public class SuccessResponse<T> extends BaseResponse<T> {

    private T result;

    public static <T> SuccessResponse<T> of(BaseCode code, T result) {
        return SuccessResponse.<T>builder()
                              .message(code.getMessage())
                              .status(code.getStatus())
                              .timestamp(LocalDateTime.now())
                              .result(result)
                              .build();
    }

    public static SuccessResponse<Object> of(BaseCode code) {
        return SuccessResponse.builder()
                              .message(code.getMessage())
                              .status(code.getStatus())
                              .timestamp(LocalDateTime.now())
                              .result(null)
                              .build();
    }
}
