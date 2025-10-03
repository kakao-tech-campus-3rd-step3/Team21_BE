package com.kakao.uniscope.college.dto;

import com.kakao.uniscope.college.entity.College;

public record CollegeSummaryDto(
        Long collegeSeq,
        String collegeName,
        String collegeIntro,
        int departmentCount
) {
    public static CollegeSummaryDto from(College college) {
        int deptCount = college.getDepartments() != null ? college.getDepartments().size() : 0;

        return new CollegeSummaryDto(
                college.getCollegeSeq(),
                college.getCollegeName(),
                college.getCollegeIntro(),
                deptCount
        );
    }
}
