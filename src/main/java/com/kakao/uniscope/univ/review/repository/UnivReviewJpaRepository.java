package com.kakao.uniscope.univ.review.repository;

import com.kakao.uniscope.univ.entity.University;
import com.kakao.uniscope.univ.review.entity.UnivReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnivReviewJpaRepository extends JpaRepository<UnivReview, Long> {
    Page<UnivReview> findByUniversity(University university, Pageable pageable);
}
