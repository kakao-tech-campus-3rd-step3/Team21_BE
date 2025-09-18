package com.kakao.uniscope.department.entity;

import com.kakao.uniscope.college.entity.College;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "DEPT")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DEPT_SEQ")
    private Long deptSeq;

    @Column(name = "DEPT_NAME")
    private String deptName;

    @Column(name = "HOME_PAGE")
    private String homePage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COLLEGE_SEQ")
    private College college;
}
