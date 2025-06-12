package com.catchtable.api.user.DTO;

import com.catchtable.api.segment.domain.UserSegmentEntity;
import com.catchtable.response.implement.ResponseMetaDataImpl;
import com.catchtable.response.interfaces.ResponseMetaData;
import java.util.List;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public class UserSegmentResponseDTO {

    private List<Long> segmentIds;
    private ResponseMetaData responseMetaData;

    public static UserSegmentResponseDTO of(Page<UserSegmentEntity> userSegmentEntities) {
        UserSegmentResponseDTO dto = new UserSegmentResponseDTO();
        dto.segmentIds = userSegmentEntities.getContent()
                                            .stream()
                                            .map(segment -> segment.getSegment()
                                                                   .getId())
                                            .toList();
        dto.responseMetaData = new ResponseMetaDataImpl(userSegmentEntities.hasNext(),
            userSegmentEntities.getSize(),
            userSegmentEntities.getNumber() + 1);
        return dto;
    }
}
