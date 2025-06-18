package com.catchtable.api.segment.DTO;

import com.catchtable.api.segment.domain.SegmentConditions;
import com.catchtable.exception.exception.SegmentException;
import com.catchtable.response.error.SegmentErrorCode;
import jakarta.validation.constraints.NotBlank;
import java.util.Map;
import java.util.Set;
import lombok.Getter;

@Getter
public class CreateSegmentRequestDTO {

    @NotBlank
    private String name;

    @NotBlank
    private Map<String, Map<String, Integer>> condition;

    public Boolean isValid() {
        condition.keySet()
                 .forEach(key -> {
                     if (!SegmentConditions.isValid(key)) {
                         throw new SegmentException(SegmentErrorCode.UNEXPECTED_PARAM);
                     }
                     Map<String, Integer> req = condition.get(key);
                     Set<String> compares = SegmentConditions.valueOf(key)
                                                             .getCompares();

                     req.keySet()
                        .forEach(segment -> {
                            if (!compares.contains(segment)) {
                                throw new SegmentException(SegmentErrorCode.UNEXPECTED_PARAM);
                            }
                        });
                 });
        return true;
    }
}
