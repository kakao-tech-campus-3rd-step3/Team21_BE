package com.kakao.uniscope.professor.entity;

import com.kakao.uniscope.department.entity.Department;
import com.kakao.uniscope.lecture.entity.Lecture;
import com.kakao.uniscope.professor.review.entity.ProfReview;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROF_SEQ")
    private Long profSeq;

    @Column(name = "PROF_NAME")
    private String profName;

    @Column(name = "PROF_EMAIL")
    private String profEmail;

    @Column(name = "HOME_PAGE")
    private String homePage;

    @Column(name = "OFFICE")
    private String office; // 사무실 정보(공5601)

    @Column(name = "POSITION")
    private String position; // 교수 직책 (교수, 조교수, 명예교수 등)

    @Column(name = "IMAGE_URL")
    private String imageUrl;

    @Column(name = "MAJOR")
    private String major;

    @Column(name = "RESEARCH_FIELD")
    private String researchField;

    @Column(name = "DEGREE")
    private String degree;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEPT_SEQ")
    private Department department;

    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Lecture> lectures = new ArrayList<>();

    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProfReview> profReviews = new ArrayList<>();
}
