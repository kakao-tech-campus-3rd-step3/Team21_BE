package com.kakao.uniscope.department.dto;

import com.kakao.uniscope.department.entity.Department;

public record DepartmentDto(
        Long deptSeq,
        String deptName,
        String homePage,
        String deptAddress,
        String deptTel,
        String deptEmail,
        String deptEstablishedYear,
        String deptIntro
) {
    public static DepartmentDto from(Department department) {
        return new DepartmentDto(
                department.getDeptSeq(),
                department.getDeptName(),
                department.getHomePage(),
                department.getDeptAddress(),
                department.getDeptTel(),
                department.getDeptEmail(),
                department.getDeptEstablishedYear(),
                department.getDeptIntro()
        );
    }
}
