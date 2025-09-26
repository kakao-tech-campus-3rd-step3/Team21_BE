package com.kakao.uniscope.univ.univReview;

import com.kakao.uniscope.common.exception.ResourceNotFoundException;
import com.kakao.uniscope.univ.FakeUnivRepository;
import com.kakao.uniscope.univ.review.dto.UnivReviewResponseDto;
import com.kakao.uniscope.univ.review.service.UnivReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.*;

public class UnivReviewServiceTest {

    private UnivReviewService univReviewService;

    @BeforeEach
    void setUp() {
        FakeUnivRepository fakeUnivRepository = new FakeUnivRepository();
        FakeUnivReviewRepository fakeUnivReviewRepository = new FakeUnivReviewRepository();
        this.univReviewService = new UnivReviewService(fakeUnivRepository, fakeUnivReviewRepository);
    }

    @Test
    void 대학_평가_목록_조회_성공() {
        Long univSeq = 1L;
        Pageable pageable = Pageable.ofSize(10).withPage(0);

        UnivReviewResponseDto result = univReviewService.getAllUnivReviews(univSeq, pageable);

        assertNotNull(result);
        assertFalse(result.content().isEmpty());
    }

    @Test
    void 대학은_존재하지만_리뷰가_없는_경우_빈_페이지_반환() {
        Long univSeq = 2L;
        Pageable pageable = Pageable.ofSize(10).withPage(0);

        UnivReviewResponseDto result = univReviewService.getAllUnivReviews(univSeq, pageable);

        assertNotNull(result);
        assertTrue(result.content().isEmpty());
        assertEquals(0, result.totalPages());
        assertEquals(0, result.totalElements());
    }

    @Test
    void 존재하지_않는_대학_리뷰_조회_시_예외_발생() {
        Long univSeq = 999L;
        Pageable pageable = Pageable.ofSize(10).withPage(0);

        assertThrows(ResourceNotFoundException.class, () -> univReviewService.getAllUnivReviews(univSeq, pageable));
    }
}
