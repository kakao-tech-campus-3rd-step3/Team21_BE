package com.kakao.uniscope.univ.review.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record ReviewCreateResponse(
        Long reviewSeq,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime timestamp
) {
    public static ReviewCreateResponse of(Long reviewSeq) {
        return new ReviewCreateResponse(
                reviewSeq,
                LocalDateTime.now()
        );
    }
}
