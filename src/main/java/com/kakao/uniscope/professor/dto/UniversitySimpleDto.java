package com.kakao.uniscope.professor.dto;

import com.kakao.uniscope.univ.entity.University;

public record UniversitySimpleDto(
        Long id,
        String name
) {
    public static UniversitySimpleDto from(University university) {
        return new UniversitySimpleDto(
                university.getUnivSeq(),
                university.getName()
        );
    }
}
