package com.kakao.uniscope.department.repository;

import com.kakao.uniscope.department.entity.Department;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentJpaRepository extends JpaRepository<Department, Long> {

    @EntityGraph(attributePaths = {"professors", "departmentCareerFields"})
    Optional<Department> findWithFullDetailsByDeptSeq(Long deptSeq);
}
