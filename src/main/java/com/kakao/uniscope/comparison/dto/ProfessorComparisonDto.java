package com.kakao.uniscope.comparison.dto;

import java.util.List;
import lombok.Builder;

@Builder
public record ProfessorComparisonDto(
        Long profSeq,
        String profName,
        String univName,
        String deptName,
        ProfessorScoreDto scores,
        List<SemesterDto> semesterDto
) {
}
