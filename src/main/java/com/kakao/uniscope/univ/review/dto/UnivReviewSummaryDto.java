package com.kakao.uniscope.univ.review.dto;

import com.kakao.uniscope.univ.review.entity.UnivReview;

import java.time.LocalDateTime;

public record UnivReviewSummaryDto(
        Long univReviewSeq,
        LocalDateTime createDate
) {
    public static UnivReviewSummaryDto from(UnivReview univReview) {
        return new UnivReviewSummaryDto(
                univReview.getUnivReviewSeq(),
                univReview.getCreateDate()
        );
    }
}
