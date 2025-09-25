package com.kakao.uniscope.univ.review.service;

import com.kakao.uniscope.common.exception.ResourceNotFoundException;
import com.kakao.uniscope.univ.entity.University;
import com.kakao.uniscope.univ.repository.UnivRepository;
import com.kakao.uniscope.univ.review.dto.UnivReviewDto;
import com.kakao.uniscope.univ.review.dto.UnivReviewResponseDto;
import com.kakao.uniscope.univ.review.entity.UnivReview;
import com.kakao.uniscope.univ.review.repository.UnivReviewRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UnivReviewService {

    private final UnivRepository univRepository;
    private final UnivReviewRepository univReviewRepository;

    public UnivReviewService(UnivRepository univRepository, UnivReviewRepository univReviewRepository) {
        this.univRepository = univRepository;
        this.univReviewRepository = univReviewRepository;
    }

    @Transactional(readOnly = true)
    public UnivReviewResponseDto getAllUnivReviews(Long univSeq, Pageable pageable) {
        University university = univRepository.findById(univSeq)
                .orElseThrow(() -> new ResourceNotFoundException(univSeq + "에 해당하는 대학교를 찾을 수 없습니다."));

        Page<UnivReview> reviewPage = univReviewRepository.findByUniversity(university, pageable);

        Page<UnivReviewDto> reviewDtoPage = reviewPage.map(UnivReviewDto::from);

        return UnivReviewResponseDto.from(reviewDtoPage);
    }
}
