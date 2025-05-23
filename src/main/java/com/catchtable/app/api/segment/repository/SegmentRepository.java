package com.catchtable.app.api.segment.repository;

import com.catchtable.app.api.segment.domain.SegmentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SegmentRepository extends JpaRepository<SegmentEntity, Integer> {

    @Query("SELECT s FROM SegmentEntity s WHERE s.isActive = false")
    Page<SegmentEntity> findAllIncludeDeactivate(Pageable pageable);
}
