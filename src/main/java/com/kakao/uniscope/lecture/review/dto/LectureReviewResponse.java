package com.kakao.uniscope.lecture.review.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public record LectureReviewResponse(
        Long reviewSeq,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime timestamp
) {
    public static LectureReviewResponse of(Long reviewSeq, LocalDateTime timestamp) {
        return new LectureReviewResponse(
                reviewSeq,
                timestamp
        );
    }
}
