package com.kakao.uniscope.college.service;

import com.kakao.uniscope.college.dto.CollegeDetailsResponseDto;
import com.kakao.uniscope.college.dto.DepartmentsByCollegeResponseDto;
import com.kakao.uniscope.college.entity.College;
import com.kakao.uniscope.college.repository.CollegeRepository;
import com.kakao.uniscope.common.exception.ResourceNotFoundException;
import com.kakao.uniscope.department.dto.DepartmentDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CollegeService {

    private final CollegeRepository collegeRepository;

    public CollegeService(CollegeRepository collegeRepository) {
        this.collegeRepository = collegeRepository;
    }

    @Transactional(readOnly = true)
    public DepartmentsByCollegeResponseDto getDepartmentsByCollege(Long collegeSeq) {
        College college = collegeRepository.findWithDepartmentsByCollegeSeq(collegeSeq)
                .orElseThrow(() -> new ResourceNotFoundException(collegeSeq + "에 해당하는 단과대학을 찾을 수 없습니다."));

        List<DepartmentDto> departmentDtos = college.getDepartments().stream()
                .map(DepartmentDto::from)
                .toList();

        return new DepartmentsByCollegeResponseDto(departmentDtos);
    }

    @Transactional(readOnly = true)
    public CollegeDetailsResponseDto getCollegeDetails(Long collegeSeq) {
        College college = collegeRepository.findWithUniversityByCollegeSeq(collegeSeq)
                .orElseThrow(() -> new ResourceNotFoundException(collegeSeq + "에 해당하는 단과대학을 찾을 수 없습니다."));

        return CollegeDetailsResponseDto.from(college);
    }
}
