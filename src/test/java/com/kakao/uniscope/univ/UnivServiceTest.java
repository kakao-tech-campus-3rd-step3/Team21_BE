package com.kakao.uniscope.univ;

import com.kakao.uniscope.college.entity.College;
import com.kakao.uniscope.college.repository.CollegeRepository;
import com.kakao.uniscope.univ.dto.UnivResponseDto;
import com.kakao.uniscope.univ.entity.University;
import com.kakao.uniscope.univ.repository.UnivRepository;
import com.kakao.uniscope.univ.review.entity.UnivReview;
import com.kakao.uniscope.univ.review.repository.UnivReviewRepository;
import com.kakao.uniscope.univ.service.UnivService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UnivServiceTest {

    @Mock
    private UnivRepository univRepository;

    @Mock
    private CollegeRepository collegeRepository;

    @Mock
    private UnivReviewRepository univReviewRepository;

    @InjectMocks
    private UnivService univService;

    @Test
    @DisplayName("대학교 상세 정보 조회 성공")
    void getUniversityDetails_Success() {
        Long univSeq = 1L;

        // 대학 엔티티
        University mockUniv = mock(University.class);
        when(mockUniv.getUnivSeq()).thenReturn(univSeq);
        when(mockUniv.getName()).thenReturn("충남대학교");

        // 단과 대학 엔티티
        College mockCollege = mock(College.class);
        when(mockCollege.getCollegeSeq()).thenReturn(1L);
        when(mockCollege.getCollegeName()).thenReturn("공과대학");
        List<College> mockColleges = List.of(mockCollege);

        // 대학 평가 엔티티
        UnivReview mockReview = mock(UnivReview.class);
        when(mockReview.getUnivReviewSeq()).thenReturn(10L);
        when(mockReview.getOverallScore()).thenReturn(4);
        when(mockReview.getReviewText()).thenReturn("대학 리뷰");
        when(mockReview.getCreateDate()).thenReturn(LocalDateTime.now());
        List<UnivReview> mockReviews = List.of(mockReview);

        when(univRepository.findById(univSeq)).thenReturn(Optional.of(mockUniv));
        when(collegeRepository.findByUniversity_UnivSeq(univSeq)).thenReturn(mockColleges);
        when(univReviewRepository.findTop3ByUniversityOrderByCreateDateDesc(any(University.class))).thenReturn(mockReviews);

        UnivResponseDto result = univService.getUniversityDetails(univSeq);

        // then: 결과 검증
        assertNotNull(result);
        assertEquals("충남대학교", result.university().name());
        assertEquals(1, result.colleges().size());
        assertEquals(1, result.univReviews().size());

        // 리포지토리 메서드가 올바르게 호출되었는지 확인
        verify(univRepository, times(1)).findById(univSeq);
        verify(collegeRepository, times(1)).findByUniversity_UnivSeq(univSeq);
        verify(univReviewRepository, times(1)).findTop3ByUniversityOrderByCreateDateDesc(any(University.class));
    }

    @Test
    @DisplayName("대학교 상세 정보 조회 실패 - 리소스 없음")
    void getUniversityDetails_NotFound() {
        Long univSeq = 999L;
        when(univRepository.findById(univSeq)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> univService.getUniversityDetails(univSeq));
        verify(univRepository, times(1)).findById(univSeq);
    }
}
