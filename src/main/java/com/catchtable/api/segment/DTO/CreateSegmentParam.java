package com.catchtable.api.segment.DTO;

import java.util.Map;

public record CreateSegmentParam(String name,
                                 @jakarta.validation.constraints.NotBlank Map<String, Map<String, Integer>> condition) {

}
