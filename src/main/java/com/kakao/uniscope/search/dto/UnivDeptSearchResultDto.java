package com.kakao.uniscope.search.dto;

import com.kakao.uniscope.college.entity.College;
import com.kakao.uniscope.department.entity.Department;
import com.kakao.uniscope.univ.entity.University;

public record UnivDeptSearchResultDto(
        Long univSeq,
        String univName,
        String collegeName,
        String deptName,
        String description,
        Integer studentCount,
        Double rating,
        Integer reviewCount // 학교평가 개수
) {
    public static UnivDeptSearchResultDto from(
            University univ,
            College college,
            Department dept,
            Double rating,
            Integer reviewCount
    ) {
        return new UnivDeptSearchResultDto(
                univ.getUnivSeq(),
                univ.getName(),
                college.getCollegeName(),
                dept.getDeptName(),
                dept.getDeptIntro() != null ? dept.getDeptIntro() : "학과 소개",
                dept.getDeptStudentNum() != null ? dept.getDeptStudentNum() : 0,
                rating,
                reviewCount
        );
    }
}
