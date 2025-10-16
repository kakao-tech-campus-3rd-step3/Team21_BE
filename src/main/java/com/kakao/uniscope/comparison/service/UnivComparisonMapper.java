package com.kakao.uniscope.comparison.service;

import com.kakao.uniscope.comparison.dto.ChartScoreDto;
import com.kakao.uniscope.comparison.dto.UnivYearlyRatingTrendDto;
import com.kakao.uniscope.comparison.dto.UnivYearlyScoreDto;
import com.kakao.uniscope.comparison.dto.UniversityComparisonDto;
import com.kakao.uniscope.univ.entity.University;
import com.kakao.uniscope.univ.review.entity.UnivReview;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UnivComparisonMapper {

    public UniversityComparisonDto toUniversityComparisonDto(University univ) {
        ChartScoreDto scores = calculateScores(univ.getReviews());

        return UniversityComparisonDto.builder()
                .univSeq(univ.getUnivSeq())
                .univName(univ.getName())
                .address(univ.getAddress())
                .tel(univ.getTel())
                .scores(scores)
                .build();
    }

    public ChartScoreDto calculateScores(Set<UnivReview> reviews) {
        if (reviews == null || reviews.isEmpty()) {
            return ChartScoreDto.empty();
        }

        double foodAvg = reviews.stream().mapToInt(r -> r.getFoodScore() != null ? r.getFoodScore() : 0).average().orElse(0.0);
        double dormAvg = reviews.stream().mapToInt(r -> r.getDormScore() != null ? r.getDormScore() : 0).average().orElse(0.0);
        double convAvg = reviews.stream().mapToInt(r -> r.getConvScore() != null ? r.getConvScore() : 0).average().orElse(0.0);
        double campusAvg = reviews.stream().mapToInt(r -> r.getCampusScore() != null ? r.getCampusScore() : 0).average().orElse(0.0);
        double welfareAvg = reviews.stream().mapToInt(r -> r.getWelfareScore() != null ? r.getWelfareScore() : 0).average().orElse(0.0);

        return new ChartScoreDto(foodAvg, dormAvg, convAvg, campusAvg, welfareAvg);
    }

    // 연도별 추이 데이터 생성
    public List<UnivYearlyRatingTrendDto> generateYearlyTrends(List<University> universities) {
        List<UnivYearlyRatingTrendDto> trends = new ArrayList<>();

        int currentYear = LocalDateTime.now().getYear();
        int startYear = currentYear - 7;

        for (int year = startYear; year <= currentYear; year++) {
            List<UnivYearlyScoreDto> yearlyScores = new ArrayList<>();

            for (University univ : universities) {
                Double avgScore = calculateAverageForYear(univ.getReviews(), year);
                yearlyScores.add(new UnivYearlyScoreDto(univ.getUnivSeq(), avgScore));
            }

            trends.add(new UnivYearlyRatingTrendDto(year, yearlyScores));
        }

        trends.sort(Comparator.comparing(UnivYearlyRatingTrendDto::year));
        return trends;
    }

    private Double calculateAverageForYear(Set<UnivReview> reviews, int targetYear) {
        if (reviews == null || reviews.isEmpty()) return 0.0;

        Set<UnivReview> reviewsInYear = reviews.stream()
                .filter(r -> r.getCreateDate() != null && r.getCreateDate().getYear() == targetYear)
                .collect(Collectors.toSet());

        // University 엔티티의 calculateAvgRating 정적 메서드를 재사용하여 중복 제거
        return University.calculateAvgRating(reviewsInYear);
    }
}
