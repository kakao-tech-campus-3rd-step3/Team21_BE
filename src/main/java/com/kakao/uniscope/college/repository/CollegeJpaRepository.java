package com.kakao.uniscope.college.repository;

import com.kakao.uniscope.college.entity.College;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CollegeJpaRepository extends JpaRepository<College, Long> {
    List<College> findByUniversity_UnivSeq(Long univSeq);

    @EntityGraph(attributePaths = "departments")
    Optional<College> findById(Long collegeSeq);
}
