package com.kakao.uniscope.professor.review.entity;

import com.kakao.uniscope.professor.entity.Professor;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PROF_REVIEW")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfReview {

    @Id
    @Column(name = "PROF_REVIEW_SEQ")
    private Long profReviewSeq;

    @Column(name = "THESIS_PERF")
    private Integer thesisPerf;

    @Column(name = "RESEARCH_PERF")
    private Integer researchPerf;

    @Column(name = "THESIS_REVIEW")
    private String thesisReview;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROF_SEQ")
    private Professor professor;
}