package com.catchtable.response;

import com.catchtable.base.BaseCode;
import com.catchtable.base.BaseResponse;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ErrorResponse extends BaseResponse<Object> {

    private String error;

    public static ErrorResponse of(BaseCode error) {
        return ErrorResponse.builder()
                            .error(error.toString())
                            .message(error.getMessage())
                            .status(error.getStatus())
                            .timestamp(LocalDateTime.now())
                            .build();
    }
}
