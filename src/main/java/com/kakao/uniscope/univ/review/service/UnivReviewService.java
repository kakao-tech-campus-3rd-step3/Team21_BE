package com.kakao.uniscope.univ.review.service;

import com.kakao.uniscope.common.exception.ResourceNotFoundException;
import com.kakao.uniscope.univ.entity.University;
import com.kakao.uniscope.univ.repository.UnivRepository;
import com.kakao.uniscope.univ.review.dto.ReviewCreateResponse;
import com.kakao.uniscope.univ.review.dto.UnivReviewDto;
import com.kakao.uniscope.univ.review.dto.UnivReviewRequest;
import com.kakao.uniscope.univ.review.dto.UnivReviewResponseDto;
import com.kakao.uniscope.univ.review.entity.UnivReview;
import com.kakao.uniscope.univ.review.repository.UnivReviewRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
public class UnivReviewService {

    private final UnivRepository univRepository;
    private final UnivReviewRepository univReviewRepository;

    public UnivReviewService(UnivRepository univRepository, UnivReviewRepository univReviewRepository) {
        this.univRepository = univRepository;
        this.univReviewRepository = univReviewRepository;
    }

    public UnivReviewResponseDto getAllUnivReviews(Long univSeq, Pageable pageable) {
        University university = univRepository.findWithFullDetailsByUnivSeq(univSeq)
                .orElseThrow(() -> new ResourceNotFoundException(univSeq + "에 해당하는 대학교를 찾을 수 없습니다."));

        Page<UnivReview> reviewPage = univReviewRepository.findByUniversity(university, pageable);

        Page<UnivReviewDto> reviewDtoPage = reviewPage.map(UnivReviewDto::from);

        return UnivReviewResponseDto.from(reviewDtoPage);
    }

    @Transactional(readOnly = false)
    public ReviewCreateResponse createReview(UnivReviewRequest request) {
        University university = univRepository.findWithFullDetailsByUnivSeq(request.univSeq())
                .orElseThrow(() -> new ResourceNotFoundException(request.univSeq() + "에 해당하는 대학교를 찾을 수 없습니다."));

        UnivReview newReview = UnivReview.builder()
                .university(university)
                .foodScore(request.food())
                .dormScore(request.dormitory())
                .convScore(request.convenience())
                .campusScore(request.campus())
                .overallScore(request.overall())
                .reviewText(request.reviewText())
                .createDate(LocalDateTime.now())
                .build();

        UnivReview savedReview = univReviewRepository.save(newReview);

        return ReviewCreateResponse.of(savedReview.getUnivReviewSeq());
    }
}
