package com.kakao.uniscope.univ.review.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kakao.uniscope.univ.review.entity.UnivReview;

import java.time.LocalDateTime;

public record UnivReviewDto(
        Long univReviewSeq,
        Integer foodScore,
        Integer dormScore,
        Integer convScore,
        Integer campusScore,
        Integer welfareScore,
        String reviewText,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime createDate
) {
    public static UnivReviewDto from(UnivReview univReview) {
        return new UnivReviewDto(
                univReview.getUnivReviewSeq(),
                univReview.getFoodScore(),
                univReview.getDormScore(),
                univReview.getConvScore(),
                univReview.getCampusScore(),
                univReview.getWelfareScore(),
                univReview.getReviewText(),
                univReview.getCreateDate()
        );
    }
}
