package com.kakao.uniscope.department.repository;

import com.kakao.uniscope.department.entity.Department;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class DepartmentRepositoryImpl implements DepartmentRepository {

    private final DepartmentJpaRepository departmentJpaRepository;

    public DepartmentRepositoryImpl(DepartmentJpaRepository departmentJpaRepository) {
        this.departmentJpaRepository = departmentJpaRepository;
    }

    @Override
    public Optional<Department> findWithFullDetailsByDeptSeq(Long deptSeq) {
        return departmentJpaRepository.findWithFullDetailsByDeptSeq(deptSeq);
    }
}
