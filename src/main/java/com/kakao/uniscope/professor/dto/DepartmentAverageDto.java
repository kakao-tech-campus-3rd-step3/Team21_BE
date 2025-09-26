package com.kakao.uniscope.professor.dto;

public record DepartmentAverageDto(
        Double thesisPerformance,
        Double researchPerformance,
        Double homework,
        Double lectureDifficulty,
        Double examDifficulty
) {
    public static DepartmentAverageDto of(Double thesisPerf, Double researchPerf,
            Double homework, Double lecDifficulty, Double examDifficulty) {
        return new DepartmentAverageDto(
                thesisPerf != null ? thesisPerf : 0.0,
                researchPerf != null ? researchPerf : 0.0,
                homework != null ? homework : 0.0,
                lecDifficulty != null ? lecDifficulty : 0.0,
                examDifficulty != null ? examDifficulty : 0.0
        );
    }
}
