package com.kakao.uniscope.univ.repository;

import com.kakao.uniscope.univ.entity.University;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UnivJpaRepository extends JpaRepository<University, Long> {
    @EntityGraph(attributePaths = "colleges")
    Optional<University> findWithCollegesByUnivSeq(Long univSeq);

    @EntityGraph(attributePaths = {"colleges.departments", "reviews"})
    Optional<University> findWithFullDetailsByUnivSeq(Long univSeq);

    @EntityGraph(attributePaths = "reviews")
    List<University> findWithReviewsByUnivSeqIn(List<Long> univSeqs);
}
