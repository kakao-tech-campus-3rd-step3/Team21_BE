package com.kakao.uniscope.department.repository;

import com.kakao.uniscope.department.entity.Department;

import java.util.Optional;

public interface DepartmentRepository {

    // deptSeq를 통해 학과의 상세 정보를 조회
    Optional<Department> findWithFullDetailsByDeptSeq(Long deptSeq);
}
