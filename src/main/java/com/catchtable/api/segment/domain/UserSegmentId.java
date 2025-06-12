package com.catchtable.api.segment.domain;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
public class UserSegmentId implements Serializable {

    private Long userId;
    private Long segmentId;


    public UserSegmentId(Long userId, Long segmentId) {
        this.userId = userId;
        this.segmentId = segmentId;
    }

    public static UserSegmentId of(Long userId, Long segmentId) {
        return new UserSegmentId(userId, segmentId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserSegmentId that = (UserSegmentId) o;
        return Objects.equals(userId, that.userId) && Objects.equals(segmentId, that.segmentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, segmentId);
    }
}
