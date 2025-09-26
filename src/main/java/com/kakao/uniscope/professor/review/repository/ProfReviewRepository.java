package com.kakao.uniscope.professor.review.repository;

import com.kakao.uniscope.professor.review.entity.ProfReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfReviewRepository extends JpaRepository<ProfReview, Long> {

    // 논문 실적 평균
    @Query("SELECT AVG(pr.thesisPerf) FROM ProfReview pr WHERE pr.professor.profSeq = :profSeq")
    Double findAvgThesisPerf(@Param("profSeq") Long profSeq);

    // 연구 실적 평균
    @Query("SELECT AVG(pr.researchPerf) FROM ProfReview pr WHERE pr.professor.profSeq = :profSeq")
    Double findAvgResearchPerf(@Param("profSeq") Long profSeq);

    // 학과별 교수평가 통계
    @Query("SELECT AVG(pr.thesisPerf) FROM ProfReview pr " +
            "JOIN pr.professor p WHERE p.department.deptSeq = :deptSeq")
    Double findDeptAvgThesisPerf(@Param("deptSeq") Long deptSeq);

    @Query("SELECT AVG(pr.researchPerf) FROM ProfReview pr " +
            "JOIN pr.professor p WHERE p.department.deptSeq = :deptSeq")
    Double findDeptAvgResearchPerf(@Param("deptSeq") Long deptSeq);
}
