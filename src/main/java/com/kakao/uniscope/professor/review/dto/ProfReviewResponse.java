package com.kakao.uniscope.professor.review.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kakao.uniscope.professor.review.entity.ProfReview;
import java.time.LocalDateTime;

public record ProfReviewResponse(
        Long reviewSeq,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime timestamp
) {
    public static ProfReviewResponse of(Long reviewSeq, LocalDateTime timestamp) {
        return new ProfReviewResponse(
                reviewSeq,
                timestamp
        );
    }
}
