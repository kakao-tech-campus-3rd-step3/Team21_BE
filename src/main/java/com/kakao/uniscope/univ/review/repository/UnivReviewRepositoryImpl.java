package com.kakao.uniscope.univ.review.repository;

import com.kakao.uniscope.univ.entity.University;
import com.kakao.uniscope.univ.review.entity.UnivReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class UnivReviewRepositoryImpl implements UnivReviewRepository {

    private final UnivReviewJpaRepository univReviewJpaRepository;

    public UnivReviewRepositoryImpl(UnivReviewJpaRepository univReviewJpaRepository) {
        this.univReviewJpaRepository = univReviewJpaRepository;
    }

    @Override
    public Page<UnivReview> findByUniversity(University university, Pageable pageable) {
        return univReviewJpaRepository.findByUniversity(university, pageable);
    }

    @Override
    public UnivReview save(UnivReview univReview) {
        return univReviewJpaRepository.save(univReview);
    }
}
