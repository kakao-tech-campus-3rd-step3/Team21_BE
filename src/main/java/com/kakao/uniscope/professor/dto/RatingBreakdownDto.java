package com.kakao.uniscope.professor.dto;

public record RatingBreakdownDto(
        Double thesisPerformance,    // 논문 실적
        Double researchPerformance,  // 연구 실적
        Double homework,             // 과제량 평균
        Double lectureDifficulty,    // 수업 난이도 평균
        Double examDifficulty        // 시험 난이도 평균
) {
    public static RatingBreakdownDto of(Double thesisPerf, Double researchPerf,
            Double homework, Double lecDifficulty, Double examDifficulty) {
        return new RatingBreakdownDto(
                thesisPerf != null ? thesisPerf : 0.0,
                researchPerf != null ? researchPerf : 0.0,
                homework != null ? homework : 0.0,
                lecDifficulty != null ? lecDifficulty : 0.0,
                examDifficulty != null ? examDifficulty : 0.0
        );
    }
}
