package com.kakao.uniscope.department.careerField.dto;

import com.kakao.uniscope.department.careerField.entity.CareerField;

public record CareerFieldDto(
        Long careerFieldSeq,
        String fieldName
) {
    public static CareerFieldDto from(CareerField careerField) {
        return new CareerFieldDto(
                careerField.getCareerFieldSeq(),
                careerField.getFieldName()
        );
    }
}
