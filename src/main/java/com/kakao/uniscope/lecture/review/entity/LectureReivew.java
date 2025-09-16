package com.kakao.uniscope.lecture.review.entity;

import com.kakao.uniscope.lecture.entity.Lecture;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "LECTURE_REVIEW")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LectureReivew {

    @Id
    @Column(name = "LEC_REVIEW_SEQ")
    private Long lecReviewSeq;

    @Column(name = "HOMEWORK")
    private Integer homework;

    @Column(name = "LEC_DIFFICULTY")
    private Integer lecDifficulty;

    @Column(name = "GRADE_DISTRIBUTION")
    private Integer gradeDistribution;

    @Column(name = "SEMESTER")
    private String semester;

    @Column(name = "EXAM_DIFFICULTY")
    private Integer examDifficulty;

    @Column(name = "GROUP_PROJ_REQ")
    private String groupProjReq;

    @Column(name = "OVERALL_REVIEW")
    private String overallReview;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LEC_SEQ")
    private Lecture lecture;

    @Column(name = "CREATE_DATE")
    private LocalDateTime createdDate;
}
