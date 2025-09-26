package com.kakao.uniscope.department.entity;

import com.kakao.uniscope.college.entity.College;
import com.kakao.uniscope.professor.entity.Professor;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COLLEGE_SEQ")
    private College college;

    // prof 엔티티에서 연관관계 매핑 필요
    // @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    // private List<Professor> professors = new ArrayList<>();
}
