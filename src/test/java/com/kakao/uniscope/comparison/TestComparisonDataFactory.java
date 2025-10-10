package com.kakao.uniscope.comparison;

import com.kakao.uniscope.comparison.dto.ChartScoreDto;
import com.kakao.uniscope.comparison.dto.UniversityComparisonDto;

import java.util.List;

public class TestComparisonDataFactory {

    // 대학 비교에 사용될 mock 데이터 생성
    public static List<UniversityComparisonDto> createMockComparisonData() {

        ChartScoreDto score1 = new ChartScoreDto(4.5, 3.8, 4.0, 4.2, 4.5);
        ChartScoreDto score2 = new ChartScoreDto(3.5, 4.2, 3.0, 3.5, 3.8);

        UniversityComparisonDto univ1 = UniversityComparisonDto.builder()
                .univSeq(1L)
                .univName("충남대학교")
                .address("대전광역시")
                .tel("042-111-1111")
                .scores(score1)
                .build();

        UniversityComparisonDto univ2 = UniversityComparisonDto.builder()
                .univSeq(2L)
                .univName("경북대학교")
                .address("대구광역시")
                .tel("053-222-2222")
                .scores(score2)
                .build();

        return List.of(univ1, univ2);
    }
}
