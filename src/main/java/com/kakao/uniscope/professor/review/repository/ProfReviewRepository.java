package com.kakao.uniscope.professor.review.repository;

import com.kakao.uniscope.professor.review.entity.ProfReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfReviewRepository extends JpaRepository<ProfReview, Long> {
}
