package com.kakao.uniscope.univ.univReview;

import com.kakao.uniscope.common.exception.ResourceNotFoundException;
import com.kakao.uniscope.univ.FakeUnivRepository;
import com.kakao.uniscope.univ.review.dto.ReviewCreateResponse;
import com.kakao.uniscope.univ.review.dto.UnivReviewRequest;
import com.kakao.uniscope.univ.review.dto.UnivReviewResponseDto;
import com.kakao.uniscope.univ.review.service.UnivReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.*;

public class UnivReviewServiceTest {

    private FakeUnivReviewRepository fakeUnivReviewRepository;
    private UnivReviewService univReviewService;

    @BeforeEach
    void setUp() {
        FakeUnivRepository fakeUnivRepository = new FakeUnivRepository();
        this.fakeUnivReviewRepository = new FakeUnivReviewRepository();
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

    // 대학 리뷰 작성 API 테스트

    @Test
    void 대학_리뷰_작성_성공_메서드가_정상적으로_작동하고_응답을_반환하는지_검증() {
        Long univSeq = 1L;

        UnivReviewRequest request = createRequestDto(univSeq);

        ReviewCreateResponse response = univReviewService.createReview(request);

        assertNotNull(response);
    }

    @Test
    void 대학_리뷰_작성시에_리포지토리에_리뷰가_저장되는지_검증() {
        Long univSeq = 1L;
        UnivReviewRequest request = createRequestDto(univSeq);

        int initialReviewCount = fakeUnivReviewRepository.getReviews().size();

        ReviewCreateResponse response = univReviewService.createReview(request);

        // 리뷰 개수가 1 증가했는지 확인
        assertEquals(initialReviewCount + 1, fakeUnivReviewRepository.getReviews().size());

        // 저장된 리뷰 내용이 요청 데이터와 일치하는지 확인 (리뷰 텍스트 확인)
        assertEquals(request.reviewText(), fakeUnivReviewRepository.getReviews().getLast().getReviewText());
    }

    private UnivReviewRequest createRequestDto(Long univSeq) {
        return new UnivReviewRequest(
                univSeq, 5, 4, 3, 4, 5, "유효한 리뷰 텍스트."
        );
    }
}
