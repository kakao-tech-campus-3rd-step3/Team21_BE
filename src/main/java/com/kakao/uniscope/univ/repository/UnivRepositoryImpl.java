package com.kakao.uniscope.univ.repository;

import com.kakao.uniscope.univ.entity.University;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UnivRepositoryImpl implements UnivRepository {

    private final UnivJpaRepository univJpaRepository;

    @Override
    public Optional<University> findById(Long univSeq) {
        return univJpaRepository.findById(univSeq);
    }

    @Override
    public Optional<University> findWithCollegesByUnivSeq(Long univSeq) {
        return univJpaRepository.findWithCollegesByUnivSeq(univSeq);
    }

    @Override
    public List<University> findAll() {
        return univJpaRepository.findAll();
    }
}
