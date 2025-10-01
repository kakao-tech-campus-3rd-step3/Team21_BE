package com.kakao.uniscope.department.careerField.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class DepartmentCareerFieldId implements Serializable {
    @Column(name = "DEPT_SEQ")
    private Long deptSeq;

    @Column(name = "CAREER_FIELD_SEQ")
    private Long fieldSeq;
}
