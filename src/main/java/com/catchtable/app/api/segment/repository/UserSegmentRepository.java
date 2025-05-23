package com.catchtable.app.api.segment.repository;

import com.catchtable.app.api.segment.domain.UserSegmentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSegmentRepository extends JpaRepository<UserSegmentEntity, Integer> {

    Page<UserSegmentEntity> findAllByUserId(Long user_id, Pageable pageable);
}
