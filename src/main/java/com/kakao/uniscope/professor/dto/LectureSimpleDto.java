package com.kakao.uniscope.professor.dto;

import com.kakao.uniscope.lecture.entity.Lecture;

public record LectureSimpleDto(
        Long id,
        String name,
        String engLecYn,
        String pnf,
        String relYn,
        Integer reviewCount
) {
    public static LectureSimpleDto from(Lecture lecture) {
        return new LectureSimpleDto(
                lecture.getLecSeq(),
                lecture.getLecName(),
                lecture.getEngLecYn(),
                lecture.getPnf(),
                lecture.getPnf(),
                lecture.getLectureReviews().size()
        );
    }
}
