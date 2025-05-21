package com.catchtable.api.segment.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Map;

public class SegmentPostRequestDTO {

    @NotBlank
    private String name;

    @NotNull
    private Map<String, Object> condition;
}
