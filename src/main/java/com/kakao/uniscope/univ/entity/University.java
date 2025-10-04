package com.kakao.uniscope.univ.entity;

import com.kakao.uniscope.college.entity.College;
import com.kakao.uniscope.univ.review.entity.UnivReview;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "UNIV")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class University {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UNIV_SEQ")
    private Long univSeq;

    @Column(name = "NAME")
    private String name;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "TEL")
    private String tel;

    @Column(name = "HOME_PAGE")
    private String homePage;

    @Column(name = "IMAGE_URL")
    private String imageUrl;

    @Column(name = "ESTABLISHED_YEAR")
    private String establishedYear;

    @Column(name = "STUDENT_NUM")
    private Integer totalStudent;

    @Column(name = "CAMPUS_CNT")
    private Integer campusCnt;

    @OneToMany(mappedBy = "university", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("collegeSeq ASC")
    @Builder.Default
    private Set<College> colleges = new HashSet<>();

    @OneToMany(mappedBy = "university", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("univReviewSeq ASC")
    @Builder.Default
    private Set<UnivReview> reviews = new HashSet<>();

    public int getCollegeCount() {
        return this.colleges.size();
    }

    public int getDepartmentCount() {
        return this.colleges.stream()
                .mapToInt(college -> college.getDepartments().size())
                .sum();
    }

    public long getReviewCount() {
        return this.reviews.size();
    }

    public double getAverageRating() {
        if (this.reviews.isEmpty()) {
            return 0.0;
        }
        return this.reviews.stream()
                .mapToInt(UnivReview::getOverallScore)
                .average()
                .orElse(0.0);
    }
}
