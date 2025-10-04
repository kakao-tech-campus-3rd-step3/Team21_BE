package com.kakao.uniscope.department.dto;

import com.kakao.uniscope.professor.entity.Professor;

public record ProfessorDto(
        Long profSeq,
        String profName,
        String profEmail,
        String position,
        String office,
        String imageUrl
) {
    public static ProfessorDto from(Professor professor) {
        return new ProfessorDto(
                professor.getProfSeq(),
                professor.getProfName(),
                professor.getProfEmail(),
                professor.getPosition(),
                professor.getOffice(),
                professor.getImageUrl()
        );
    }
}
