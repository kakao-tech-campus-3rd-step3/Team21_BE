package com.kakao.uniscope.lecture.controller;

import com.kakao.uniscope.lecture.review.dto.LectureReviewRequest;
import com.kakao.uniscope.lecture.review.dto.LectureReviewResponse;
import com.kakao.uniscope.lecture.review.service.LectureReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reviews/lecture")
public class LectureReviewController {

    private final LectureReviewService lectureReviewService;

    public LectureReviewController(LectureReviewService lectureReviewService) {
        this.lectureReviewService = lectureReviewService;
    }

    @PostMapping
    public ResponseEntity<LectureReviewResponse> createLectureReview(
            @RequestBody LectureReviewRequest request
    ) {
        LectureReviewResponse response = lectureReviewService.createReview(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
