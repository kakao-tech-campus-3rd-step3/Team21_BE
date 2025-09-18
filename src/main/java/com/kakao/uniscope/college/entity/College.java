package com.kakao.uniscope.college.entity;

import com.kakao.uniscope.department.entity.Department;
import com.kakao.uniscope.univ.entity.University;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "COLLEGE")
@Getter
public class College {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COLLEGE_SEQ")
    private Long collegeSeq;

    @Column(name = "COLLEGE_NAME")
    private String collegeName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UNIV_SEQ")
    private University university;

    @OneToMany(mappedBy = "college", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Department> departments = new ArrayList<>();

    protected College() {}
}
