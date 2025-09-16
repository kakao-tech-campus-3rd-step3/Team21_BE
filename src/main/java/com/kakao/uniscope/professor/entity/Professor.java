package com.kakao.uniscope.professor.entity;

import com.kakao.uniscope.college.entity.College;
import com.kakao.uniscope.lecture.entity.Lecture;
import com.kakao.uniscope.professor.review.entity.ProfReview;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PROF")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Professor {

    @Id
    @Column(name = "PROF_SEQ")
    private Long profSeq;

    @Column(name = "PROF_NAME")
    private String profName;

    @Column(name = "PROF_EMAIL")
    private String profEmail;

    @Column(name = "HOME_PAGE")
    private String homePage;

    // todo: 추후 college를 department(학과)와 연관관계 매핑해야함 -> profdto수정도 필요
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COLLEGE_SEQ")  // DB에서 이 컬럼으로 College와 연결
    private College college;

    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Lecture> lectures = new ArrayList<>();

    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProfReview> profReviews = new ArrayList<>();
}
