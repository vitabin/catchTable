package com.catchtable.api.user.service;

import com.catchtable.api.user.DTO.UserSegmentResponseDTO;
import com.catchtable.api.user.repository.UserRepository;
import com.catchtable.dynamo.domain.UserSegment;
import com.catchtable.dynamo.repository.UserSegmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserSegmentRepository userSegmentRepository;

    public UserSegmentResponseDTO getUserSegments(Long userId, Pageable page) {
        PageIterable<UserSegment> userSegments = userSegmentRepository.getManyItemsById(userId);
        return UserSegmentResponseDTO.of(userSegments);
    }
}
