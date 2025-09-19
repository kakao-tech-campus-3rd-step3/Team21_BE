package com.kakao.uniscope.lecture.review.repository;

import com.kakao.uniscope.lecture.entity.Lecture;
import com.kakao.uniscope.lecture.review.entity.LectureReivew;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureReviewRepository extends JpaRepository<LectureReivew, Long> {

    List<LectureReivew> findTop3ByLectureOrderByCreateDateDesc(Lecture Lecture);
}
