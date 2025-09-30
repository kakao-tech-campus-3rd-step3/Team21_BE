package com.kakao.uniscope.department.entity;

import com.kakao.uniscope.college.entity.College;
import com.kakao.uniscope.department.careerField.entity.DepartmentCareerField;
import com.kakao.uniscope.professor.entity.Professor;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "DEPT")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DEPT_SEQ")
    private Long deptSeq;

    @Column(name = "DEPT_NAME")
    private String deptName;

    @Column(name = "HOME_PAGE")
    private String homePage;

    @Column(name = "DEPT_ADDRESS")
    private String deptAddress;

    @Column(name = "DEPT_TEL")
    private String deptTel;

    @Column(name = "DEPT_EMAIL")
    private String deptEmail;

    @Column(name = "DEPT_ESTABLISHED_YEAR")
    private String deptEstablishedYear;

    @Column(name = "DEPT_INTRO")
    private String deptIntro;

    @Column(name = "DEPT_STUDENT_NUM")
    private Integer deptStudentNum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COLLEGE_SEQ")
    private College college;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Professor> professors = new ArrayList<>();

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<DepartmentCareerField> departmentCareerFields = new HashSet<>();
}
