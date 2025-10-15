package com.kakao.uniscope.comparison.dto;

import lombok.Builder;

@Builder
public record UniversityComparisonDto(
        Long univSeq,
        String univName,
        String address,
        String tel,

        // 차트 데이터
        ChartScoreDto scores
) {
}
