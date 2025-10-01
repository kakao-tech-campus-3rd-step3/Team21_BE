package com.kakao.uniscope.department.dto;

import com.kakao.uniscope.department.careerField.dto.CareerFieldDto;
import com.kakao.uniscope.department.careerField.entity.DepartmentCareerField;
import com.kakao.uniscope.department.entity.Department;

import java.util.Set;
import java.util.stream.Collectors;

public record DepartmentInfoResponse(
        Long deptSeq,
        String deptName,
        String homePage,
        String deptAddress,
        String deptTel,
        String deptEmail,
        String deptEstablishedYear,
        String deptIntro,
        Integer deptStudentNum,
        Integer professorCount,
        Set<CareerFieldDto> careerFields
) {
    public static DepartmentInfoResponse from(Department department) {

        Set<CareerFieldDto> careerFieldDtos = department.getDepartmentCareerFields().stream()
                .map(DepartmentCareerField::getCareerField)
                .map(CareerFieldDto::from)
                .collect(Collectors.toSet());

        int professorCount = department.getProfessors() != null ? department.getProfessorCount() : 0;

        return new DepartmentInfoResponse(
                department.getDeptSeq(),
                department.getDeptName(),
                department.getHomePage(),
                department.getDeptAddress(),
                department.getDeptTel(),
                department.getDeptEmail(),
                department.getDeptEstablishedYear(),
                department.getDeptIntro(),
                department.getDeptStudentNum(),
                professorCount,
                careerFieldDtos
        );
    }
}
