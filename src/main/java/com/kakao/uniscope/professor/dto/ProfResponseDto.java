package com.kakao.uniscope.professor.dto;

import com.kakao.uniscope.lecture.review.dto.LectureReviewSummaryDto;
import java.util.List;

public record ProfResponseDto(
        ProfessorDto professor,
        List<LectureReviewSummaryDto> recentLectureReviews
) {
}
