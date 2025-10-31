package com.kakao.uniscope.lecture.review.dto;

public record LectureReviewRequest(
        Long lecSeq,
        Integer year, // 년도
        Integer semester, // 학기
        Integer lecDifficulty, // 강의력
        Integer gradeDistribution, // 성적 잘 주는지 여부
        Integer examDifficulty, // 시험 난이도
        Integer homework, // 과제량
        String groupProjReq, // 팀플 여부
        String overallReview // 강의 총평
) {
    public String getSemesterString() {
        return year + "-" + semester;
    }
}
