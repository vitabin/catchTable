package com.catchtable.api.segment.repository;

import com.catchtable.api.segment.domain.UserSegmentEntity;
import com.catchtable.api.segment.domain.UserSegmentId;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSegmentRepository extends JpaRepository<UserSegmentEntity, Integer> {

    Optional<UserSegmentEntity> findById(UserSegmentId id);
}
