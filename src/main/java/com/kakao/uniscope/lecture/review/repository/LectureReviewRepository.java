package com.kakao.uniscope.lecture.review.repository;

import com.kakao.uniscope.lecture.review.entity.LectureReivew;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureReviewRepository extends JpaRepository<LectureReivew, Long> {

    List<LectureReivew> findTop3ByLecture_Professor_ProfSeqOrderByCreatedDateDesc(Long profSeq);

    Page<LectureReivew> findByLecture_Professor_ProfSeqOrderByCreatedDateDesc(Long profSeq, Pageable pageable);

    // 과제량 평균
    @Query("SELECT AVG(lr.homework) FROM LectureReivew lr JOIN lr.lecture l WHERE l.professor.profSeq = :profSeq")
    Double findAvgHomework(@Param("profSeq") Long profSeq);

    // 수업 난이도 평균
    @Query("SELECT AVG(lr.lecDifficulty) FROM LectureReivew lr JOIN lr.lecture l WHERE l.professor.profSeq = :profSeq")
    Double findAvgLecDifficulty(@Param("profSeq") Long profSeq);

    // 시험 난이도 평균
    @Query("SELECT AVG(lr.examDifficulty) FROM LectureReivew lr JOIN lr.lecture l WHERE l.professor.profSeq = :profSeq")
    Double findAvgExamDifficulty(@Param("profSeq") Long profSeq);

    // 교수별 전체 강의평 개수
    @Query("SELECT COUNT(lr) FROM LectureReivew lr JOIN lr.lecture l WHERE l.professor.profSeq = :profSeq")
    Integer countByProfessorId(@Param("profSeq") Long profSeq);

    // 전체 강의평 평점 평균 (강의평 기준 전체 평점용)
    @Query("SELECT AVG(CAST((lr.homework + lr.lecDifficulty + lr.examDifficulty) AS DOUBLE) / 3.0) " +
            "FROM LectureReivew lr JOIN lr.lecture l WHERE l.professor.profSeq = :profSeq")
    Double findOverallLectureRating(@Param("profSeq") Long profSeq);

    // 학과별 강의평 통계
    @Query("SELECT AVG(lr.homework) FROM LectureReivew lr " +
            "JOIN lr.lecture l JOIN l.professor p WHERE p.department.deptSeq = :deptSeq")
    Double findDeptAvgHomework(@Param("deptSeq") Long deptSeq);

    @Query("SELECT AVG(lr.lecDifficulty) FROM LectureReivew lr " +
            "JOIN lr.lecture l JOIN l.professor p WHERE p.department.deptSeq = :deptSeq")
    Double findDeptAvgLecDifficulty(@Param("deptSeq") Long deptSeq);

    @Query("SELECT AVG(lr.examDifficulty) FROM LectureReivew lr " +
            "JOIN lr.lecture l JOIN l.professor p WHERE p.department.deptSeq = :deptSeq")
    Double findDeptAvgExamDifficulty(@Param("deptSeq") Long deptSeq);
}
