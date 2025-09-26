package com.kakao.uniscope.college.dto;

import com.kakao.uniscope.department.dto.DepartmentDto;

import java.util.List;

public record DepartmentsByCollegeResponseDto(
        List<DepartmentDto> departments
) { }
