package com.catchtable.app.api.segment.domain;

import com.catchtable.app.api.segment.DTO.CreateSegmentParam;
import com.catchtable.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.Getter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Where;
import org.hibernate.type.SqlTypes;

@Entity
@Getter
@Table(name = "segment")
@Where(clause = "is_activate = true")
public class SegmentEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "`condition`")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> condition;

    private LocalDateTime deletedAt;

    public static SegmentEntity of(CreateSegmentParam reqeust) {
        SegmentEntity entity = new SegmentEntity();
        entity.name = reqeust.name();
        entity.isActive = false;
        entity.condition = reqeust.condition();
        entity.updatedAt = LocalDateTime.now();
        return entity;
    }

    public SegmentEntity update(CreateSegmentParam reqeust) {
        this.name = reqeust.name();
        this.condition = reqeust.condition();
        this.updatedAt = LocalDateTime.now();
        return this;
    }

    public SegmentEntity delete() {
        this.deletedAt = LocalDateTime.now();
        return this;
    }

    public SegmentEntity activate() {
        this.isActive = true;
        return this;
    }

    public SegmentEntity deactivate() {
        this.isActive = false;
        return this;
    }
}
