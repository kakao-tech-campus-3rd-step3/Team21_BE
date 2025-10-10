package com.kakao.uniscope.comparison.dto;

public record ChartScoreDto(
        Double foodAvg,
        Double dormitoryAvg,
        Double convenienceAvg,
        Double campusAvg,
        Double welfareAvg
) {
    public static ChartScoreDto empty() {
        return new ChartScoreDto(0.0, 0.0, 0.0, 0.0, 0.0);
    }
}
