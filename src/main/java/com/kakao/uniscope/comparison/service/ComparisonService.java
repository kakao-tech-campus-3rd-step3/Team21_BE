package com.kakao.uniscope.comparison.service;

import com.kakao.uniscope.common.exception.ResourceNotFoundException;
import com.kakao.uniscope.comparison.dto.*;
import com.kakao.uniscope.comparison.exception.ComparisonException;
import com.kakao.uniscope.univ.entity.University;
import com.kakao.uniscope.univ.repository.UnivRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ComparisonService {

    private final UnivRepository univRepository;
    private final UnivComparisonMapper univComparisonMapper;

    public ComparisonService(UnivRepository univRepository) {
        this.univRepository = univRepository;
        this.univComparisonMapper = new UnivComparisonMapper();
    }

    // 대학 비교를 위한 로직
    public List<UniversityComparisonDto> getUniversitiesComparisonData(List<Long> univSeqs) {
        validateUnivSeqs(univSeqs);

        List<University> universities = univRepository.findWithReviewsByUnivSeqIn(univSeqs);

        if (universities.size() != univSeqs.size()) {
            throw new ResourceNotFoundException("요청된 일부 대학 정보를 찾을 수 없거나 존재하지 않습니다.");
        }

        return universities.stream()
                .map(univComparisonMapper::toUniversityComparisonDto)
                .collect(Collectors.toList());
    }

    // 연도별 평균 평점 추이 데이터 조회 로직
    public UnivRatingTrendResponseDto getUnivRatingTrendData(List<Long> univSeqs) {
        validateUnivSeqs(univSeqs);

        List<University> universities = univRepository.findWithReviewsByUnivSeqIn(univSeqs);

        if (universities.size() != univSeqs.size()) {
            throw new ResourceNotFoundException("요청된 일부 대학 정보를 찾을 수 없거나 존재하지 않습니다.");
        }

        List<UnivYearlyRatingTrendDto> trends = univComparisonMapper.generateYearlyTrends(universities);

        return new UnivRatingTrendResponseDto(trends);
    }

    private void validateUnivSeqs(List<Long> univSeqs) {
        if (univSeqs == null || univSeqs.isEmpty() || univSeqs.size() > 2) {
            throw new ComparisonException("비교할 대학 Seq는 1개 이상 2개 이하로 제공되어야 합니다.");
        }
    }
}
