package com.kakao.uniscope.professor.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.kakao.uniscope.lecture.review.dto.LectureReviewSummaryDto;
import com.kakao.uniscope.professor.dto.CollegeSimpleDto;
import com.kakao.uniscope.professor.dto.ProfResponseDto;
import com.kakao.uniscope.professor.dto.ProfessorDto;
import com.kakao.uniscope.professor.dto.RatingBreakdownDto;
import com.kakao.uniscope.professor.dto.UniversitySimpleDto;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

public class ProfController {

    @Test
    @DisplayName("Controller 응답 구조 검증 - 수동으로 Service 결과 확인")
    void controllerResponseStructure() {
        // given
        var universityDto = new UniversitySimpleDto(1L, "충남대학교");
        var collegeDto = new CollegeSimpleDto(1L, "공과대학");
        var ratingBreakdown = new RatingBreakdownDto(4.0, 4.5, 3.5, 4.0, 3.8);
        var professorDto = new ProfessorDto(1L, "김철수", universityDto, collegeDto,
                "kimcs@cnu.ac.kr", 3.96, ratingBreakdown);

        var lectureReview = new LectureReviewSummaryDto(1L, "자료구조", "2024-1",
                3, 4, 3, 4, "N","좋은 강의", LocalDateTime.now());

        var expectedResponse = new ProfResponseDto(professorDto, List.of(lectureReview));

        // when
        ResponseEntity<ProfResponseDto> response = ResponseEntity.ok(expectedResponse);

        // then
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().professor().name()).isEqualTo("김철수");
        assertThat(response.getBody().recentLectureReviews()).hasSize(1);
    }
}
