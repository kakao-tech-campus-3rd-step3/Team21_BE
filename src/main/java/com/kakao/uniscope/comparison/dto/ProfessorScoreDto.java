package com.kakao.uniscope.comparison.dto;

public record ProfessorScoreDto(
        Double theisPerformance,
        Double researchPerformance,
        Double homework,
        Double lectureDifficulty,
        Double examDifficulty
) {
    public static ProfessorScoreDto empty() {
        return new ProfessorScoreDto(0.0, 0.0, 0.0, 0.0, 0.0);
    }
}
