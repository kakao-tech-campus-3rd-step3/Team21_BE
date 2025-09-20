package com.kakao.uniscope.college.repository;

import com.kakao.uniscope.college.entity.College;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CollegeRepositoryImpl implements CollegeRepository {

    private final CollegeJpaRepository collegeJpaRepository;

    @Override
    public Optional<College> findById(Long collegeSeq) {
        return collegeJpaRepository.findById(collegeSeq);
    }

    @Override
    public List<College> findByUniversitySeq(Long univSeq) {
        return collegeJpaRepository.findByUniversity_UnivSeq(univSeq);
    }

}
