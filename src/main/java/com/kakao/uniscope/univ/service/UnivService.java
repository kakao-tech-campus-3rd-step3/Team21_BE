package com.kakao.uniscope.univ.service;

import com.kakao.uniscope.college.dto.CollegeDto;
import com.kakao.uniscope.college.entity.College;
import com.kakao.uniscope.common.exception.ResourceNotFoundException;
import com.kakao.uniscope.univ.dto.UnivResponseDto;
import com.kakao.uniscope.univ.dto.UniversityDto;
import com.kakao.uniscope.univ.entity.University;
import com.kakao.uniscope.univ.repository.UnivRepository;
import com.kakao.uniscope.univ.review.dto.UnivReviewSummaryDto;
import com.kakao.uniscope.univ.review.entity.UnivReview;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnivService {

    private final UnivRepository univRepository;

    public UnivService(UnivRepository univRepository) {
        this.univRepository = univRepository;
    }

    public UnivResponseDto getUniversityDetails(Long univSeq) {
        University university = univRepository.findById(univSeq)
                .orElseThrow(() -> new ResourceNotFoundException(univSeq + "에 해당하는 대학교를 찾을 수 없습니다."));

        List<College> colleges = university.getColleges();
        List<UnivReview> recentReviews = university.getReviews().stream()
                .sorted((r1, r2) -> r2.getCreateDate().compareTo(r1.getCreateDate()))
                .limit(3)
                .toList();

        List<CollegeDto> collegeDtos = colleges.stream()
                .map(CollegeDto::from)
                .toList();

        List<UnivReviewSummaryDto> reviewDtos = recentReviews.stream()
                .map(UnivReviewSummaryDto::from)
                .toList();

        return new UnivResponseDto(UniversityDto.from(university), collegeDtos, reviewDtos);
    }
}
