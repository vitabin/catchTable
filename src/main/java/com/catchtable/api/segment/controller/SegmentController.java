package com.catchtable.api.segment.controller;

import com.catchtable.api.segment.DTO.SegmentPostRequestDTO;
import com.catchtable.api.segment.domain.SegmentEntity;
import com.catchtable.response.SuccessResponse;
import com.catchtable.response.implement.ResponseMetaDataImpl;
import com.catchtable.response.success.SuccessCode;
import java.util.ArrayList;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/segments")
public class SegmentController {

    @GetMapping
    public SuccessResponse<ArrayList<SegmentEntity>> getAllSegments() {
        return SuccessResponse.of(SuccessCode.SUCCESS, new ArrayList<>(),
            new ResponseMetaDataImpl(true, 10, 11));
    }

    @PostMapping
    public SuccessResponse<Object> createSegment(@RequestBody SegmentPostRequestDTO req) {
        return SuccessResponse.of(SuccessCode.WITHOUT_CONTENT);
    }

    @PutMapping("/{segment_id}")
    public SuccessResponse<Object> updateSegment(@PathVariable("segment_id") Integer segmentId,
        @RequestBody SegmentPostRequestDTO req) {
        return SuccessResponse.of(SuccessCode.WITHOUT_CONTENT);
    }

    @DeleteMapping("/{segment_id}")
    public SuccessResponse<Object> deleteSegment(@PathVariable("segment_id") Integer segmentId) {
        return SuccessResponse.of(SuccessCode.WITHOUT_CONTENT);
    }

    @PatchMapping("/{segment_id}/activate")
    public SuccessResponse<Object> activateSegment(@PathVariable("segment_id") Integer segmentId) {
        return SuccessResponse.of(SuccessCode.WITHOUT_CONTENT);
    }

    @PatchMapping("/{segment_id}/deactivate")
    public SuccessResponse<Object> deactivateSegment(
        @PathVariable("segment_id") Integer segmentId) {
        return SuccessResponse.of(SuccessCode.WITHOUT_CONTENT);
    }
}
