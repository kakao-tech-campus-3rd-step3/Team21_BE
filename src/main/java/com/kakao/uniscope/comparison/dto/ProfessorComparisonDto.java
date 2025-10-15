package com.kakao.uniscope.comparison.dto;

import lombok.Builder;

@Builder
public record ProfessorComparisonDto(
        Long profSeq,
        String profName,
        String univName,
        String deptName,
        ProfessorScoreDto scores
) {
}
