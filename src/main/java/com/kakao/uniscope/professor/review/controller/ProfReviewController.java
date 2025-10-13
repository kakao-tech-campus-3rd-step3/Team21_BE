package com.kakao.uniscope.professor.review.controller;

import com.kakao.uniscope.professor.review.dto.ProfReviewRequest;
import com.kakao.uniscope.professor.review.dto.ProfReviewResponse;
import com.kakao.uniscope.professor.review.service.ProfReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reviews/prof")
@RequiredArgsConstructor
public class ProfReviewController {

    private final ProfReviewService profReviewService;

    @PostMapping
    public ResponseEntity<ProfReviewResponse> createProfReview(
            @RequestBody ProfReviewRequest request
    ) {
        ProfReviewResponse response = profReviewService.createReview(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}