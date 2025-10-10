package com.kakao.uniscope.univ.review.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record UnivReviewRequest(
        @NotNull(message = "대학 고유 번호는 필수입니다.")
        Long univSeq,

        @NotNull(message = "학식 평점은 필수입니다.")
        @Min(1) @Max(5)
        Integer food,

        @NotNull(message = "기숙사 평점은 필수입니다.")
        @Min(1) @Max(5)
        Integer dormitory,

        @NotNull(message = "편의시설 평점은 필수입니다.")
        @Min(1) @Max(5)
        Integer convenience,

        @NotNull(message = "캠퍼스 환경 평점은 필수입니다.")
        @Min(1) @Max(5)
        Integer campus,

        @NotNull(message = "학생 복지 평점은 필수입니다.")
        @Min(1) @Max(5)
        Integer welfare,

        String reviewText
) { }
