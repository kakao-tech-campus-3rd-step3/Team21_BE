package com.kakao.uniscope.comparison;

import com.kakao.uniscope.common.exception.ResourceNotFoundException;
import com.kakao.uniscope.comparison.dto.UniversityComparisonDto;
import com.kakao.uniscope.comparison.exception.ComparisonException;
import com.kakao.uniscope.comparison.service.ComparisonService;
import com.kakao.uniscope.univ.FakeUnivRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ComparisonServiceTest {

    private ComparisonService comparisonService;
    private FakeUnivRepository fakeUnivRepository;

    @BeforeEach
    void setUp() {
        this.fakeUnivRepository = new FakeUnivRepository();
        this.comparisonService = new ComparisonService(this.fakeUnivRepository);
    }

    @Test
    void 대학_두개_비교_조회_성공() {

        List<Long> univSeqs = List.of(1L, 2L);

        List<UniversityComparisonDto> results = comparisonService.getUniversitiesComparisonData(univSeqs);

        assertNotNull(results);
        assertEquals(2, results.size());
    }

    @Test
    void 평균_평점_및_점수_계산_정확성_검증() {

        List<Long> univSeqs = List.of(1L, 2L);

        List<UniversityComparisonDto> results = comparisonService.getUniversitiesComparisonData(univSeqs);

        UniversityComparisonDto univ1Result = results.stream()
                .filter(u -> u.univSeq().equals(1L))
                .findFirst().orElseThrow();

        UniversityComparisonDto univ2Result = results.stream()
                .filter(u -> u.univSeq().equals(2L))
                .findFirst().orElseThrow();

        assertEquals(4.5, univ1Result.scores().foodAvg(), 0.001, "Food 평균은 4.5여야 함.");
        assertEquals(4.5, univ1Result.scores().dormitoryAvg(), 0.001, "Dorm 평균은 4.5여야 함.");
        assertEquals(0.0, univ2Result.scores().foodAvg(), 0.001, "리뷰가 없으므로 세부 항목 평균은 0.0이어야 함.");
    }

    @Test
    void 대학_하나만_조회했을때_데이터_크기는_1이어야_한다() {

        List<Long> univSeqs = List.of(1L);

        List<UniversityComparisonDto> results = comparisonService.getUniversitiesComparisonData(univSeqs);

        assertEquals(1, results.size(), "요청한 ID 개수만큼 데이터가 반환되어야 함.");
    }

    @Test
    void 존재하지_않는_대학_요청_시_ResourceNotFoundException_발생() {

        List<Long> univSeqs = List.of(1L, 999L);

        assertThrows(ResourceNotFoundException.class,
                () -> comparisonService.getUniversitiesComparisonData(univSeqs),
                "조회된 대학 수가 요청된 ID 수와 다를 때 ResourceNotFoundException이 발생해야 함.");
    }

    @Test
    void 비교_seq가_2개를_초과하면_ComparisionException_발생() {

        List<Long> invalidSeqs = List.of(1L, 2L, 3L);

        assertThrows(ComparisonException.class,
                () -> comparisonService.getUniversitiesComparisonData(invalidSeqs),
                "비교 Seq가 2개를 초과하면 ComparisonException이 발생해야 함.");
    }
}
