package com.kakao.uniscope.search.dto;

import com.kakao.uniscope.college.entity.College;
import com.kakao.uniscope.department.entity.Department;
import com.kakao.uniscope.professor.entity.Professor;
import com.kakao.uniscope.univ.entity.University;

public record UnivProfSearchResultDto(
        Long univSeq,
        String univName,
        String profName,
        String collegeName,
        String deptName,
        String position,
        Double rating,
        Integer reviewCount // 교수 평가 개수
) {
    public static UnivProfSearchResultDto from(
            University univ,
            College college,
            Department dept,
            Professor prof,
            Double rating,
            Integer reviewCount
    ) {
        return new UnivProfSearchResultDto(
                univ.getUnivSeq(),
                univ.getName(),
                prof.getProfName(),
                college.getCollegeName(),
                dept.getDeptName(),
                prof.getPosition() != null ? prof.getPosition() : "교수",
                rating,
                reviewCount
        );
    }
}
