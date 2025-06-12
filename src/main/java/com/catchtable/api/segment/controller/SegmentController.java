package com.catchtable.api.segment.controller;

import com.catchtable.annotation.Pagination;
import com.catchtable.api.segment.DTO.CreateSegmentParam;
import com.catchtable.api.segment.DTO.CreateSegmentRequestDTO;
import com.catchtable.api.segment.domain.SegmentEntity;
import com.catchtable.api.segment.service.SegmentService;
import com.catchtable.response.SuccessResponse;
import com.catchtable.response.implement.ResponseMetaDataImpl;
import com.catchtable.response.success.SuccessCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/segments")
@RequiredArgsConstructor
public class SegmentController {

    private final SegmentService segmentService;

    @GetMapping
    public SuccessResponse<List<SegmentEntity>> getAllSegments(
        @Pagination Pageable pageable,
        @RequestParam(required = false, defaultValue = "true") Boolean onlyActivate) {
        Page<SegmentEntity> segments = segmentService.getAllSegments(pageable, onlyActivate);

        return SuccessResponse.of(SuccessCode.SUCCESS, segments.getContent(),
            new ResponseMetaDataImpl(segments.hasNext(), segments.getSize(),
                segments.getNumber() + 1));
    }

    @PostMapping
    public SuccessResponse<Object> createSegment(@RequestBody CreateSegmentRequestDTO req) {
        segmentService.createSegment(new CreateSegmentParam(req.getName(), req.getCondition()));

        return SuccessResponse.of(SuccessCode.WITHOUT_CONTENT);
    }

    @PutMapping("/{segment_id}")
    public SuccessResponse<Object> updateSegment(@PathVariable("segment_id") Integer segmentId,
        @RequestBody CreateSegmentRequestDTO req) {
        segmentService.updateSegment(segmentId,
            new CreateSegmentParam(req.getName(), req.getCondition()));

        return SuccessResponse.of(SuccessCode.WITHOUT_CONTENT);
    }

    @DeleteMapping("/{segment_id}")
    public SuccessResponse<Object> deleteSegment(@PathVariable("segment_id") Integer segmentId) {
        segmentService.deleteSegment(segmentId);
        return SuccessResponse.of(SuccessCode.WITHOUT_CONTENT);
    }

    @PatchMapping("/{segment_id}/activate")
    public SuccessResponse<Object> activateSegment(@PathVariable("segment_id") Integer segmentId) {
        segmentService.activateSegment(segmentId);
        return SuccessResponse.of(SuccessCode.WITHOUT_CONTENT);
    }

    @PatchMapping("/{segment_id}/deactivate")
    public SuccessResponse<Object> deactivateSegment(
        @PathVariable("segment_id") Integer segmentId) {
        segmentService.deactivateSegment(segmentId);
        return SuccessResponse.of(SuccessCode.WITHOUT_CONTENT);
    }
}
