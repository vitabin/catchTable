package com.catchtable.app.api.segment.repository;

import com.catchtable.app.api.segment.domain.UserSegmentEntity;
import com.catchtable.app.api.segment.domain.UserSegmentId;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSegmentRepository extends JpaRepository<UserSegmentEntity, Integer> {

    Optional<UserSegmentEntity> findById(UserSegmentId id);
}
