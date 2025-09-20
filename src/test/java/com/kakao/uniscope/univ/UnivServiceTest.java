package com.kakao.uniscope.univ;

import com.kakao.uniscope.common.exception.ResourceNotFoundException;
import com.kakao.uniscope.univ.dto.UnivResponseDto;
import com.kakao.uniscope.univ.review.dto.UnivReviewSummaryDto;
import com.kakao.uniscope.univ.service.UnivService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UnivServiceTest {

    private UnivService univService;

    @BeforeEach
    void setUp() {
        FakeUnivRepository fakeUnivRepository = new FakeUnivRepository();
        univService = new UnivService(fakeUnivRepository);
    }

    @Test
    @DisplayName("대학교 상세 정보 조회 시, 기본 정보와 리스트가 정상 반환된다")
    void getUniversityDetails_ReturnsDataSuccessfully() {
        // given
        Long univSeq = 1L;

        // when
        UnivResponseDto result = univService.getUniversityDetails(univSeq);

        // then
        assertNotNull(result);
        assertEquals("충남대학교", result.university().name());
        assertEquals(2, result.colleges().size());
        assertEquals(3, result.univReviews().size());
    }

    @Test
    @DisplayName("존재하지 않는 대학교 조회 시 ResourceNotFoundException 발생")
    void getUniversityDetails_NotFound() {
        assertThrows(ResourceNotFoundException.class,
                () -> univService.getUniversityDetails(999L));
    }

    @Test
    @DisplayName("리뷰는 최신순으로 정렬되어 반환된다")
    void getUniversityDetails_ReviewsAreSortedByDate() {
        // given
        Long univSeq = 1L;

        // when
        UnivResponseDto result = univService.getUniversityDetails(univSeq);

        // then
        List<UnivReviewSummaryDto> reviews = result.univReviews();
        for (int i = 0; i < reviews.size() - 1; i++) {
            assertTrue(
                    reviews.get(i).createDate().isAfter(reviews.get(i + 1).createDate())
                            || reviews.get(i).createDate().isEqual(reviews.get(i + 1).createDate())
            );
        }
    }
}
