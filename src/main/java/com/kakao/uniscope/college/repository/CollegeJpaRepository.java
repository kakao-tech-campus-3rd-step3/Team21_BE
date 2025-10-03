package com.kakao.uniscope.college.repository;

import com.kakao.uniscope.college.entity.College;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CollegeJpaRepository extends JpaRepository<College, Long> {

    @EntityGraph(attributePaths = {"departments.professors"})
    Optional<College> findWithDepartmentsByCollegeSeq(Long collegeSeq);

    @EntityGraph(attributePaths = "university")
    Optional<College> findWithUniversityByCollegeSeq(Long collegeSeq);
}
