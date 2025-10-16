package com.kakao.uniscope.comparison.dto;

public record SemesterDto(
        String semester,
        Double overallAvg
) {
    public static SemesterDto of(String semester, Double overallAvg) {
        return new SemesterDto(
                semester,
                overallAvg != null ? Math.round(overallAvg * 100.0) / 100.0 : 0.0
        );
    }
}
