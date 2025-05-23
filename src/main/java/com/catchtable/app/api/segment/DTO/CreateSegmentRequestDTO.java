package com.catchtable.app.api.segment.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Map;
import lombok.Getter;

@Getter
public class CreateSegmentRequestDTO {

    @NotBlank
    private String name;

    @NotNull
    private Map<String, Object> condition;
}
