package com.kakao.uniscope.lecture.review.repository;

import com.kakao.uniscope.lecture.review.entity.LectureReivew;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureReviewRepository extends JpaRepository<LectureReivew, Long> {

    List<LectureReivew> findTop3ByLecture_Professor_ProfSeqOrderByCreatedDateDesc(Long profSeq);

    // 과제량 평균
    @Query("SELECT AVG(lr.homework) FROM LectureReivew lr JOIN lr.lecture l WHERE l.professor.profSeq = :profSeq")
    Double findAvgHomework(@Param("profSeq") Long profSeq);

    // 수업 난이도 평균
    @Query("SELECT AVG(lr.lecDifficulty) FROM LectureReivew lr JOIN lr.lecture l WHERE l.professor.profSeq = :profSeq")
    Double findAvgLecDifficulty(@Param("profSeq") Long profSeq);

    // 시험 난이도 평균
    @Query("SELECT AVG(lr.examDifficulty) FROM LectureReivew lr JOIN lr.lecture l WHERE l.professor.profSeq = :profSeq")
    Double findAvgExamDifficulty(@Param("profSeq") Long profSeq);
}
