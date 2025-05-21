package com.catchtable.api.segment.service;

import com.catchtable.api.segment.repository.SegmentRepository;
import com.catchtable.api.segment.repository.UserSegmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SegmentService {

    private final SegmentRepository segmentRepository;
    private final UserSegmentRepository userSegmentRepository;
}
