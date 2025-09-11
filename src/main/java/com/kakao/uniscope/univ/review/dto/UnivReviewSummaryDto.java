package com.kakao.uniscope.univ.review.dto;

import com.kakao.uniscope.univ.review.entity.UnivReview;

import java.time.LocalDateTime;

public record UnivReviewSummaryDto(
        Long univReviewSeq,
        Integer overallScore,
        String reviewText,
        LocalDateTime createDate
) {
    public static UnivReviewSummaryDto from(UnivReview univReview) {
        return new UnivReviewSummaryDto(
                univReview.getUnivReviewSeq(),
                univReview.getOverallScore(),
                univReview.getReviewText(),
                univReview.getCreateDate()
        );
    }
}
