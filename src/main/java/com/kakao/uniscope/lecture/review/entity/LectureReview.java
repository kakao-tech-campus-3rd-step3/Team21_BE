package com.kakao.uniscope.lecture.review.entity;

import com.kakao.uniscope.lecture.entity.Lecture;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "LECTURE_REVIEW")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LectureReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LEC_REVIEW_SEQ")
    private Long lecReviewSeq;

    @Column(name = "SEMESTER")
    private String semester; // 수강 학기

    @Column(name = "LEC_DIFFICULTY")
    private Integer lecDifficulty; // 강의력

    @Column(name = "GRADE_DISTRIBUTION")
    private Integer gradeDistribution; // 성적 잘주는지

    @Column(name = "EXAM_DIFFICULTY")
    private Integer examDifficulty; // 시험 난이도

    @Column(name = "HOMEWORK")
    private Integer homework; // 과제량

    @Column(name = "GROUP_PROJ_REQ")
    private String groupProjReq; // 팀플 여부

    @Column(name = "OVERALL_REVIEW")
    private String overallReview; // 강의 총평

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LEC_SEQ")
    private Lecture lecture;

    @Column(name = "CREATE_DATE")
    private LocalDateTime createdDate;
}
