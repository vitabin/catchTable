package com.catchtable.api.segment.DTO;

import java.util.Map;

public record CreateSegmentParam(String name, Map<String, Object> condition) {

}
