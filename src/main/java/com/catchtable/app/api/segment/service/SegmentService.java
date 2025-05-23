package com.catchtable.app.api.segment.service;

import com.catchtable.app.api.segment.DTO.CreateSegmentParam;
import com.catchtable.app.api.segment.domain.SegmentEntity;
import com.catchtable.app.api.segment.repository.SegmentRepository;
import com.catchtable.app.api.segment.repository.UserSegmentRepository;
import com.catchtable.exception.exception.SegmentException;
import com.catchtable.response.error.SegmentErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SegmentService {

    private final SegmentRepository segmentRepository;
    private final UserSegmentRepository userSegmentRepository;

    public Page<SegmentEntity> getAllSegments(Pageable page, Boolean onlyActivate) {
        if (!onlyActivate) {
            return segmentRepository.findAllIncludeDeactivate(page);
        }
        return segmentRepository.findAll(page);
    }

    public void createSegment(CreateSegmentParam reqeust) {
        segmentRepository.save(SegmentEntity.of(reqeust));
    }

    public void updateSegment(Integer segmentId, CreateSegmentParam reqeust) {
        SegmentEntity entity = segmentRepository.findById(segmentId)
                                                .orElseThrow(() -> new SegmentException(
                                                    SegmentErrorCode.NOT_FOUND));
        segmentRepository.save(entity.update(reqeust));
    }

    public void deleteSegment(Integer segmentId) {
        SegmentEntity entity = segmentRepository.findById(segmentId)
                                                .orElseThrow(() -> new SegmentException(
                                                    SegmentErrorCode.NOT_FOUND));
        segmentRepository.save(entity.delete());
    }

    public void activateSegment(Integer segmentId) {
        SegmentEntity entity = segmentRepository.findById(segmentId)
                                                .orElseThrow(() -> new SegmentException(
                                                    SegmentErrorCode.NOT_FOUND));
        segmentRepository.save(entity.activate());
    }

    public void deactivateSegment(Integer segmentId) {
        SegmentEntity entity = segmentRepository.findById(segmentId)
                                                .orElseThrow(() -> new SegmentException(
                                                    SegmentErrorCode.NOT_FOUND));
        segmentRepository.save(entity.deactivate());
    }
}
