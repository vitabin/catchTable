package com.catchtable.api.segment.domain;

import com.catchtable.api.user.domain.UserEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "user_segment", indexes = {
    @Index(name = "idx_user_id", columnList = "user_id")
})
public class UserSegmentEntity {

    @EmbeddedId
    private UserSegmentId id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @MapsId("segmentId")
    @JoinColumn(name = "segment_id")
    private SegmentEntity segment;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
