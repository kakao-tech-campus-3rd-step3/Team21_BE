package com.kakao.uniscope.college.repository;

import com.kakao.uniscope.college.entity.College;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CollegeRepositoryImpl implements CollegeRepository {

    private final CollegeJpaRepository collegeJpaRepository;

    @Override
    public Optional<College> findWithDepartmentsByCollegeSeq(Long collegeSeq) {
        return collegeJpaRepository.findWithDepartmentsByCollegeSeq(collegeSeq);
    }

    @Override
    public Optional<College> findWithUniversityByCollegeSeq(Long collegeSeq) {
        return collegeJpaRepository.findWithUniversityByCollegeSeq(collegeSeq);
    }
}
