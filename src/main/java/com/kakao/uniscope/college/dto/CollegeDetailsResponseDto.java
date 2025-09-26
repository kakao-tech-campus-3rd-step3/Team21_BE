package com.kakao.uniscope.college.dto;

import com.kakao.uniscope.college.entity.College;

public record CollegeDetailsResponseDto(
        Long collegeSeq,
        String collegeName,
        Integer collegeStudentNum,
        String collegeEstablishedYear,
        String collegeTel,
        String collegeHomePage
) {
    public static CollegeDetailsResponseDto from(College college) {
        return new CollegeDetailsResponseDto(
                college.getCollegeSeq(),
                college.getCollegeName(),
                college.getCollegeStudentNum(),
                college.getCollegeEstablishedYear(),
                college.getCollegeTel(),
                college.getCollegeHomePage()
        );
    }
}
