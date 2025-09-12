package com.kakao.uniscope.univ.dto;

import com.kakao.uniscope.college.dto.CollegeDto;
import com.kakao.uniscope.univ.review.dto.UnivReviewSummaryDto;

import java.util.List;

public record UnivResponseDto(
        UniversityDto university,
        List<CollegeDto> colleges,
        List<UnivReviewSummaryDto> univReviews
) {}
