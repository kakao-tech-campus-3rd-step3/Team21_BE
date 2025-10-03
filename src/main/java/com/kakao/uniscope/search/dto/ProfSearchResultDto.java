package com.kakao.uniscope.search.dto;

import com.kakao.uniscope.professor.entity.Professor;

public record ProfSearchResultDto(
        Long profSeq,
        String name,
        String position,
        String univName,
        String collegeName,
        String deptName,
        Double rating,
        Integer reviewCount
) {
    public static ProfSearchResultDto from(Professor prof, Double rating, Integer reviewCount) {
        return new ProfSearchResultDto(
                prof.getProfSeq(),
                prof.getProfName(),
                prof.getPosition() != null ? prof.getPosition() : "교수",
                prof.getDepartment().getCollege().getUniversity().getName(),
                prof.getDepartment().getCollege().getCollegeName(),
                prof.getDepartment().getDeptName(),
                rating,
                reviewCount
        );
    }
}
