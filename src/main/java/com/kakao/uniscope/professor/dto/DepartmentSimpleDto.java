package com.kakao.uniscope.professor.dto;

import com.kakao.uniscope.department.entity.Department;

public record DepartmentSimpleDto(
        Long id,
        String name,
        String homepage
) {
    public static DepartmentSimpleDto from(Department department) {
        return new DepartmentSimpleDto(
                department.getDeptSeq(),
                department.getDeptName(),
                department.getHomePage()
        );
    }
}
