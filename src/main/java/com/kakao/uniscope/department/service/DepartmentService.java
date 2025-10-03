package com.kakao.uniscope.department.service;

import com.kakao.uniscope.common.exception.ResourceNotFoundException;
import com.kakao.uniscope.department.dto.DepartmentInfoResponse;
import com.kakao.uniscope.department.entity.Department;
import com.kakao.uniscope.department.repository.DepartmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public DepartmentInfoResponse getDeptDetails(Long deptSeq) {
        Department department = departmentRepository.findWithFullDetailsByDeptSeq(deptSeq)
                .orElseThrow(() -> new ResourceNotFoundException(deptSeq + "에 해당하는 학과를 찾을 수 없습니다."));

        return DepartmentInfoResponse.from(department);
    }
}
