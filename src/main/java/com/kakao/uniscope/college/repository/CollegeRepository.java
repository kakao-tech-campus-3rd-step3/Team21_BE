package com.kakao.uniscope.college.repository;

import com.kakao.uniscope.college.entity.College;

import java.util.Optional;

public interface CollegeRepository {

    Optional<College> findWithDepartmentsByCollegeSeq(Long collegeSeq);
}
