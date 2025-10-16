package com.kakao.uniscope.comparison.dto;

import java.util.List;

public record UnivRatingTrendResponseDto(
        List<UnivYearlyRatingTrendDto> trends
) { }
