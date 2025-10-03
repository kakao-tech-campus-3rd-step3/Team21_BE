package com.kakao.uniscope.univ.repository;

import com.kakao.uniscope.univ.entity.University;

import java.util.List;
import java.util.Optional;

public interface UnivRepository {

    Optional<University> findById(Long univSeq);

    Optional<University> findWithCollegesByUnivSeq(Long univSeq);

    List<University> findAll();
}
