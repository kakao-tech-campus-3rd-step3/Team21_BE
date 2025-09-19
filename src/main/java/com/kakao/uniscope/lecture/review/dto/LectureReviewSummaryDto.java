package com.kakao.uniscope.lecture.review.dto;

import com.kakao.uniscope.lecture.review.entity.LectureReivew;
import java.time.LocalDateTime;

public record LectureReviewSummaryDto(
        Long id,
        String courseTitle,
        String semester,
        Integer homework,
        Integer lecDifficulty,
        Integer gradeDistribution,
        Integer examDifficulty,
        String groupProjReq,
        String content,
        LocalDateTime createdDate
) {
    public static LectureReviewSummaryDto from(LectureReivew lectureReview) {
        return new LectureReviewSummaryDto(
                lectureReview.getLecReviewSeq(),
                lectureReview.getLecture().getLecName(),
                lectureReview.getSemester(),
                lectureReview.getHomework(),
                lectureReview.getLecDifficulty(),
                lectureReview.getGradeDistribution(),
                lectureReview.getExamDifficulty(),
                lectureReview.getGroupProjReq(),
                lectureReview.getOverallReview(),
                lectureReview.getCreatedDate()
        );
    }
}
