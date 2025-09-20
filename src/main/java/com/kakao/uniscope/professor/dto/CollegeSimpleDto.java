package com.kakao.uniscope.professor.dto;

import com.kakao.uniscope.college.entity.College;

public record CollegeSimpleDto(
        Long id,
        String name
) {
    public static CollegeSimpleDto from(College college) {
        return new CollegeSimpleDto(
                college.getCollegeSeq(),
                college.getCollegeName()
        );
    }
}
