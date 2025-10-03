package com.kakao.uniscope.department.careerField.entity;

import com.kakao.uniscope.department.entity.Department;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "DEPT_CAREER_FIELD")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class DepartmentCareerField implements Serializable {

    @EmbeddedId
    private DepartmentCareerFieldId id;

    @MapsId("deptSeq")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEPT_SEQ")
    private Department department;

    @MapsId("fieldSeq")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CAREER_FIELD_SEQ")
    private CareerField careerField;
}
