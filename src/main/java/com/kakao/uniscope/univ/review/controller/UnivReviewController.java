package com.kakao.uniscope.univ.review.controller;

import com.kakao.uniscope.univ.review.dto.ReviewCreateResponse;
import com.kakao.uniscope.univ.review.dto.UnivReviewRequest;
import com.kakao.uniscope.univ.review.service.UnivReviewService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reviews/univ")
public class UnivReviewController {

    private final UnivReviewService univReviewService;

    public UnivReviewController(UnivReviewService univReviewService) {
        this.univReviewService = univReviewService;
    }

    // 대학 평가 작성 API
    @PostMapping
    public ResponseEntity<ReviewCreateResponse> createUnivReview(
            @Valid @RequestBody UnivReviewRequest request
    ) {
        ReviewCreateResponse response = univReviewService.createReview(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
