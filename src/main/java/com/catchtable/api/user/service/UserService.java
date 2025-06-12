package com.catchtable.api.user.service;

import com.catchtable.api.segment.domain.UserSegmentEntity;
import com.catchtable.api.segment.repository.UserSegmentRepository;
import com.catchtable.api.user.DTO.UserSegmentResponseDTO;
import com.catchtable.api.user.repository.UserRepository;
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
