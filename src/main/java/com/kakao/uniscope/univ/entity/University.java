package com.kakao.uniscope.univ.entity;

import com.kakao.uniscope.college.entity.College;
import com.kakao.uniscope.univ.review.entity.UnivReview;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "IMAGE")
    private String imageUrl;

    @Column(name = "ESTABLISHED_YEAR")
    private String establishedYear;

    @Column(name = "STUDENT_NUM")
    private Integer totalStudent;

    @OneToMany(mappedBy = "university", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<College> colleges = new ArrayList<>();

    @OneToMany(mappedBy = "university", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UnivReview> reviews = new ArrayList<>();
}
