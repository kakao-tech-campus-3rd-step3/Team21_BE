package com.kakao.uniscope.univ.review.dto;

import com.kakao.uniscope.univ.review.entity.UnivReview;

import java.time.LocalDateTime;

public record UnivReviewDto(
        Long univReviewSeq,
        Integer foodScore,
        Integer dormScore,
        Integer convScore,
        Integer campusScore,
        Integer overallScore,
        String reviewText,
        String createUser,
        LocalDateTime createDate
) {
    public static UnivReviewDto from(UnivReview univReview) {
        return new UnivReviewDto(
                univReview.getUnivReviewSeq(),
                univReview.getFoodScore(),
                univReview.getDormScore(),
                univReview.getConvScore(),
                univReview.getCampusScore(),
                univReview.getOverallScore(),
                univReview.getReviewText(),
                univReview.getCreateUser(),
                univReview.getCreateDate()
        );
    }
}
