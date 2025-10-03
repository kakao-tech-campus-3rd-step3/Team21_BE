package com.kakao.uniscope.univ.review.entity;

import com.kakao.uniscope.univ.entity.University;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "UNIV_REVIEW")
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UnivReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UNIV_REVIEW_SEQ")
    private Long univReviewSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UNIV_SEQ")
    private University university;

    @Column(name = "FOOD")
    private Integer foodScore;

    @Column(name = "DORM")
    private Integer dormScore;

    @Column(name = "CONV")
    private Integer convScore;

    @Column(name = "CAMPUS")
    private Integer campusScore;

    @Column(name = "OVER_ALL")
    private Integer overallScore;

    @Column(name = "REVIEW_TXT")
    private String reviewText;

    @Column(name = "CREATE_DATE")
    private LocalDateTime createDate;
}
