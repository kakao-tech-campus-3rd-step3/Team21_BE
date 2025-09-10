package com.kakao.uniscope.univ.service;

import com.kakao.uniscope.college.dto.CollegeDto;
import com.kakao.uniscope.college.entity.College;
import com.kakao.uniscope.college.repository.CollegeRepository;
import com.kakao.uniscope.univ.dto.UnivResponseDto;
import com.kakao.uniscope.univ.dto.UniversityDto;
import com.kakao.uniscope.univ.entity.University;
import com.kakao.uniscope.univ.repository.UnivRepository;
import com.kakao.uniscope.univ.review.dto.UnivReviewSummaryDto;
import com.kakao.uniscope.univ.review.entity.UnivReview;
import com.kakao.uniscope.univ.review.repository.UnivReviewRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UnivService {

    private final UnivRepository univRepository;
    private final CollegeRepository collegeRepository;
    private final UnivReviewRepository univReviewRepository;

    public UnivService(
            UnivRepository univRepository,
            CollegeRepository collegeRepository,
            UnivReviewRepository univReviewRepository
    ) {
        this.univRepository = univRepository;
        this.collegeRepository = collegeRepository;
        this.univReviewRepository = univReviewRepository;
    }

    public UnivResponseDto getUniversityDetails(Long univSeq) {
        University university = univRepository.findById(univSeq)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        List<College> colleges = collegeRepository.findByUniversity_UnivSeq(univSeq);
        List<UnivReview> recentReviews = univReviewRepository.findTop3ByUniversityOrderByCreateDateDesc(university);

        List<CollegeDto> collegeDtos = colleges.stream()
                .map(CollegeDto::from)
                .toList();

        List<UnivReviewSummaryDto> reviewDtos = recentReviews.stream()
                .map(UnivReviewSummaryDto::from)
                .toList();

        return new UnivResponseDto(UniversityDto.from(university), collegeDtos, reviewDtos);
    }
}
