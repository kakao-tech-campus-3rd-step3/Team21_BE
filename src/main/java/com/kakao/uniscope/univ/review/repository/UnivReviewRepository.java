package com.kakao.uniscope.univ.review.repository;

import com.kakao.uniscope.univ.entity.University;
import com.kakao.uniscope.univ.review.entity.UnivReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnivReviewRepository extends JpaRepository<UnivReview, Long> {
    List<UnivReview> findTop3ByUniversityOrderByCreateDateDesc(University university);
}
