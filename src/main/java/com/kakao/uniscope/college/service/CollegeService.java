package com.kakao.uniscope.college.service;

import com.kakao.uniscope.college.dto.CollegeResponseDto;
import com.kakao.uniscope.college.entity.College;
import com.kakao.uniscope.college.repository.CollegeRepository;
import com.kakao.uniscope.common.exception.ResourceNotFoundException;
import com.kakao.uniscope.department.dto.DepartmentDto;
import com.kakao.uniscope.department.entity.Department;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CollegeService {

    private final CollegeRepository collegeRepository;

    public CollegeService(CollegeRepository collegeRepository) {
        this.collegeRepository = collegeRepository;
    }

    public CollegeResponseDto getDepartmentsByCollege(Long collegeSeq) {
        College college = collegeRepository.findById(collegeSeq)
                .orElseThrow(() -> new ResourceNotFoundException(collegeSeq + "에 해당하는 단과대학을 찾을 수 없습니다."));

        List<Department> departments = college.getDepartments();

        List<DepartmentDto> departmentDtos = departments.stream()
                .map(DepartmentDto::from)
                .collect(Collectors.toList());

        return new CollegeResponseDto(departmentDtos);
    }
}
