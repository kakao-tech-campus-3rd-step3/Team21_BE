package com.kakao.uniscope.department.dto;

import com.kakao.uniscope.professor.entity.Professor;

public record ProfessorSummaryDto(
        Long profSeq,
        String profName,
        String profEmail,
        String position,
        String office,
        String imageUrl
) {
    public static ProfessorSummaryDto from(Professor professor) {
        return new ProfessorSummaryDto(
                professor.getProfSeq(),
                professor.getProfName(),
                professor.getProfEmail(),
                professor.getPosition(),
                professor.getOffice(),
                professor.getImageUrl()
        );
    }
}
