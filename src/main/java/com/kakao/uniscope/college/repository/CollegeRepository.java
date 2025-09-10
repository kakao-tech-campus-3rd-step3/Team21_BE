package com.kakao.uniscope.college.repository;

import com.kakao.uniscope.college.entity.College;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollegeRepository extends JpaRepository<College,Long> {
    List<College> findByUniversity_UnivSeq(Long univSeq);
}
