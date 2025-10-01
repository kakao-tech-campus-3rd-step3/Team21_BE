package com.kakao.uniscope.college.entity;

import com.kakao.uniscope.department.entity.Department;
import com.kakao.uniscope.univ.entity.University;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "COLLEGE")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class College {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COLLEGE_SEQ")
    private Long collegeSeq;

    @Column(name = "COLLEGE_NAME")
    private String collegeName;

    @Column(name = "COLLEGE_STUDENT_NUM")
    private Integer collegeStudentNum;

    @Column(name = "COLLEGE_ESTABLISHED_YEAR")
    private String collegeEstablishedYear;

    @Column(name = "COLLEGE_TEL")
    private String collegeTel;

    @Column(name = "COLLEGE_HOME_PAGE")
    private String collegeHomePage;

    @Column(name = "COLLEGE_INTRO")
    private String collegeIntro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UNIV_SEQ")
    private University university;

    @OneToMany(mappedBy = "college", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Department> departments = new ArrayList<>();

    public int getProfessorCount() {
        if (this.departments == null) {
            return 0;
        }

        return this.departments.stream()
                .mapToInt(department -> department.getProfessors() != null ? department.getProfessors().size() : 0)
                .sum();
    }
}
