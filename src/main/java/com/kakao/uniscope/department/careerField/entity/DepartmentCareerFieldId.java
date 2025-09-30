package com.kakao.uniscope.department.careerField.entity;

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
    private Long deptSeq;
    private Long fieldSeq;
}
