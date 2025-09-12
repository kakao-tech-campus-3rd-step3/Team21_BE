package com.kakao.uniscope.college.dto;

import com.kakao.uniscope.college.entity.College;

public record CollegeDto(
        Long collegeSeq,
        String collegeName
) {
    public static CollegeDto from(College college) {
        return new CollegeDto(
                college.getCollegeSeq(),
                college.getCollegeName()
        );
    }
}
