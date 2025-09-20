package com.kakao.uniscope.professor.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.kakao.uniscope.professor.dto.RatingBreakdownDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ProfServiceTest {

    @Test
    @DisplayName("전체 평점 계산 로직")
    void calculateOverallRating_logic() {
        // given
        RatingBreakdownDto ratings = new RatingBreakdownDto(4.0, 4.5, 3.0, 3.5, 4.0);

        // when
        double overallRating = (ratings.thesisPerformance() +
                ratings.researchPerformance() +
                ratings.homework() +
                ratings.lectureDifficulty() +
                ratings.examDifficulty()) / 5.0;

        // then
        assertThat(overallRating).isEqualTo(3.8);
    }

    @Test
    @DisplayName("RatingBreakdownDto 생성 로직")
    void createRatingBreakdown() {
        // given
        Double thesis = 4.0;
        Double research = 4.5;
        Double homework = 3.0;
        Double lecDifficulty = 3.5;
        Double examDifficulty = 4.0;

        // when
        RatingBreakdownDto result = createRatingBreakdownDto(thesis, research, homework, lecDifficulty, examDifficulty);

        // then
        assertThat(result.thesisPerformance()).isEqualTo(4.0);
        assertThat(result.researchPerformance()).isEqualTo(4.5);
        assertThat(result.homework()).isEqualTo(3.0);
        assertThat(result.lectureDifficulty()).isEqualTo(3.5);
        assertThat(result.examDifficulty()).isEqualTo(4.0);
    }

    private RatingBreakdownDto createRatingBreakdownDto(Double thesis, Double research, Double homework,
            Double lecDifficulty, Double examDifficulty) {
        return RatingBreakdownDto.of(thesis, research, homework, lecDifficulty, examDifficulty);
    }
}
