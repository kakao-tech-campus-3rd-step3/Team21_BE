package com.kakao.uniscope.professor.dto;


import com.kakao.uniscope.professor.entity.Professor;

public record ProfessorDto(
        Long id,
        String name,
        UniversitySimpleDto university,
        CollegeSimpleDto college,
        String email,
        Double overallRating,
        RatingBreakdownDto ratingBreakdown
) {
    public static ProfessorDto from(Professor professor, Double overallRating, RatingBreakdownDto ratingBreakdown) {
        return new ProfessorDto(
                professor.getProfSeq(),
                professor.getProfName(),
                UniversitySimpleDto.from(professor.getCollege().getUniversity()),
                CollegeSimpleDto.from(professor.getCollege()),
                professor.getProfEmail(),
                overallRating,
                ratingBreakdown
        );
    }
}
