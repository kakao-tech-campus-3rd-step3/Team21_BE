package com.kakao.uniscope.professor.dto;

import com.kakao.uniscope.lecture.review.dto.LectureReviewSummaryDto;
import java.util.List;
import org.springframework.data.domain.Page;

public record LectureReviewPageResponseDto(
        List<LectureReviewSummaryDto> reviews,
        boolean hasNext
) {
    public static LectureReviewPageResponseDto from(Page<LectureReviewSummaryDto> page) {
        return new LectureReviewPageResponseDto(
                page.getContent(),
                page.hasNext()
        );
    }

}
