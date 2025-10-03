package com.kakao.uniscope.department.careerField.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "CAREER_FIELD")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class CareerField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CAREER_FIELD_SEQ")
    private Long careerFieldSeq;

    @Column(name = "FIELD_NAME", length = 100, unique = true)
    private String fieldName;

    @OneToMany(mappedBy = "careerField", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<DepartmentCareerField> departmentCareerFields = new HashSet<>();
}
