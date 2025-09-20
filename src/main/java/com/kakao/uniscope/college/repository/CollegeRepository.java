package com.kakao.uniscope.college.repository;

import com.kakao.uniscope.college.entity.College;

import java.util.List;
import java.util.Optional;

public interface CollegeRepository {

    Optional<College> findById(Long collegeSeq);

    List<College> findByUniversitySeq(Long univSeq);
}
