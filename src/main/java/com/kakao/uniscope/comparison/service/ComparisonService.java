package com.kakao.uniscope.comparison.service;

import com.kakao.uniscope.common.exception.ResourceNotFoundException;
import com.kakao.uniscope.comparison.dto.ChartScoreDto;
import com.kakao.uniscope.comparison.dto.UniversityComparisonDto;
import com.kakao.uniscope.comparison.exception.ComparisonException;
import com.kakao.uniscope.univ.entity.University;
import com.kakao.uniscope.univ.repository.UnivRepository;
import com.kakao.uniscope.univ.review.entity.UnivReview;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ComparisonService {

    private final UnivRepository univRepository;

    public ComparisonService(UnivRepository univRepository) {
        this.univRepository = univRepository;
    }

    // 대학 비교를 위한 로직
    public List<UniversityComparisonDto> getUniversitiesComparisonData(List<Long> univIds) {
        if (univIds == null || univIds.isEmpty()) {
            throw new ComparisonException("비교할 대학 ID는 1개 이상 2개 이하로 제공되어야 합니다.");
        }

        List<University> universities = univRepository.findWithReviewsByUnivSeqIn(univIds);

        if (universities.size() != univIds.size()) {
            throw new ResourceNotFoundException("요청된 일부 대학 정보를 찾을 수 없거나 존재하지 않습니다.");
        }

        return universities.stream()
                .map(this::toUniversityComparisonDto)
                .collect(Collectors.toList());
    }

    private UniversityComparisonDto toUniversityComparisonDto(University univ) {

        ChartScoreDto scores = calculateScores(univ.getReviews());

        return UniversityComparisonDto.builder()
                .univSeq(univ.getUnivSeq())
                .univName(univ.getName())
                .address(univ.getAddress())
                .tel(univ.getTel())
                .scores(scores)
                .build();
    }

    private ChartScoreDto calculateScores(Set<UnivReview> reviews) {
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
}
