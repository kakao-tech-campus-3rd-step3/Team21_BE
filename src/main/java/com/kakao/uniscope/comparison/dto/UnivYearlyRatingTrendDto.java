package com.kakao.uniscope.comparison.dto;

import java.util.List;

public record UnivYearlyRatingTrendDto(
        Integer year,
        List<UnivYearlyScoreDto> scores
) { }
