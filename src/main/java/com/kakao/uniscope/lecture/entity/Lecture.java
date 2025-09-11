package com.kakao.uniscope.lecture.entity;

import com.kakao.uniscope.lecture.review.entity.LectureReivew;
import com.kakao.uniscope.professor.entity.Professor;
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
@Table(name = "LECTURE")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Lecture {

    @Id
    @Column(name = "LEC_SEQ")
    private Integer lecSeq;

    @Column(name = "LEC_NAME")
    private String lecName;

    @Column(name = "ENG_LEC_YN")
    private String engLecYn;

    @Column(name = "PF_YN")
    private String pnf;

    @Column(name = "REL_YN")
    private String relYn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROF_SEQ")
    private Professor professor;

    @OneToMany(mappedBy = "lecture", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LectureReivew> lectureReviews = new ArrayList<>();
}
