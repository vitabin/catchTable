package com.catchtable.app.api.user.service;

import com.catchtable.app.api.segment.domain.UserSegmentEntity;
import com.catchtable.app.api.segment.repository.UserSegmentRepository;
import com.catchtable.app.api.user.DTO.UserSegmentResponseDTO;
import com.catchtable.app.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserSegmentRepository userSegmentRepository;

    public UserSegmentResponseDTO getUserSegments(Long userId, Pageable page) {
        Page<UserSegmentEntity> userSegments = userSegmentRepository.findAllByUserId(userId, page);
        return UserSegmentResponseDTO.of(userSegments);
    }
}
