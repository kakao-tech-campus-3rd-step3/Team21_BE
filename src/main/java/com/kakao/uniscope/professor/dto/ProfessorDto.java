package com.kakao.uniscope.professor.dto;


import com.kakao.uniscope.professor.entity.Professor;
import java.util.List;

public record ProfessorDto(
        Long id,
        String name,
        UniversitySimpleDto university,
        CollegeSimpleDto college,
        DepartmentSimpleDto department,
        String email,
        String imageUrl,
        String office,
        String position,
        Double overallRating, // 강의평 기준 평점
        RatingBreakdownDto ratingBreakdown,
        DepartmentAverageDto departmentAverage,
        List<LectureSimpleDto> lectures,
        Integer totalReviewCount
) {
    public static ProfessorDto from(Professor professor, Double overallRating, RatingBreakdownDto ratingBreakdown,
            DepartmentAverageDto departmentAverage, List<LectureSimpleDto> lectures, Integer totalReviewCount) {
        return new ProfessorDto(
                professor.getProfSeq(),
                professor.getProfName(),
                UniversitySimpleDto.from(professor.getDepartment().getCollege().getUniversity()),
                CollegeSimpleDto.from(professor.getDepartment().getCollege()),
                DepartmentSimpleDto.from(professor.getDepartment()),
                professor.getProfEmail(),
                professor.getImageUrl(),
                professor.getOffice(),
                professor.getPosition(),
                overallRating,
                ratingBreakdown,
                departmentAverage,
                lectures,
                totalReviewCount
        );
    }
}
