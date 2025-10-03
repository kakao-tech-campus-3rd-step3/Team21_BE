package com.kakao.uniscope.search.dto;

import com.kakao.uniscope.department.entity.Department;
import com.kakao.uniscope.univ.entity.University;

public record DeptSearchResultDto(
        Long deptSeq,
        String name,
        String univName,
        String description,
        Integer studentCount,
        Double rating,
        Integer reviewCount
) {
    public static DeptSearchResultDto from(Department dept, University univ, Double rating, Integer reviewCount) {
        return new DeptSearchResultDto(
                dept.getDeptSeq(),
                dept.getDeptName(),
                univ.getName(),
                dept.getDeptIntro() != null ? dept.getDeptIntro() : "학과 소개",
                dept.getDeptStudentNum() != null ? dept.getDeptStudentNum() : 0,
                rating,
                reviewCount
        );
    }
}
