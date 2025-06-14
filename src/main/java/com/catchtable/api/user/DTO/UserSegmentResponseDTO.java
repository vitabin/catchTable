package com.catchtable.api.user.DTO;

import com.catchtable.dynamo.domain.UserSegment;
import java.util.List;
import lombok.Getter;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;

@Getter
public class UserSegmentResponseDTO {

    private List<Long> segmentIds;

    public static UserSegmentResponseDTO of(PageIterable<UserSegment> userSegments) {
        UserSegmentResponseDTO dto = new UserSegmentResponseDTO();
        dto.segmentIds = userSegments.stream()
                                     .flatMap(p -> p.items()
                                                    .stream())
                                     .toList()
                                     .stream()
                                     .map(item -> item.getSegmentId()
                                                      .longValue())
                                     .toList();

        return dto;
    }
}
