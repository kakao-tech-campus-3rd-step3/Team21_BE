package com.kakao.uniscope.professor.review.dto;

public record ProfReviewRequest(
        Long profSeq,
        Integer thesisPerformance, // 논문실적
        Integer researchPerformance, // 연구실적
        String reviewText
) {
}
