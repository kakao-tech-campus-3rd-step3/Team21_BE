package com.kakao.uniscope.univ.review.repository;

import com.kakao.uniscope.univ.entity.University;
import com.kakao.uniscope.univ.review.entity.UnivReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UnivReviewRepository {
    Page<UnivReview> findByUniversity(University university, Pageable pageable);
}
