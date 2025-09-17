package com.kakao.uniscope.professor.service;

import com.kakao.uniscope.lecture.review.dto.LectureReviewSummaryDto;
import com.kakao.uniscope.lecture.review.entity.LectureReivew;
import com.kakao.uniscope.lecture.review.repository.LectureReviewRepository;
import com.kakao.uniscope.professor.dto.ProfResponseDto;
import com.kakao.uniscope.professor.dto.ProfessorDto;
import com.kakao.uniscope.professor.dto.RatingBreakdownDto;
import com.kakao.uniscope.professor.entity.Professor;
import com.kakao.uniscope.professor.exception.ProfessorNotFoundException;
import com.kakao.uniscope.professor.repository.ProfessorRepository;
import com.kakao.uniscope.professor.review.repository.ProfReviewRepository;
import java.util.List;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfService {

    private final ProfessorRepository professorRepository;
    private final LectureReviewRepository lectureReviewRepository;
    private final ProfReviewRepository profReviewRepository;

    public ProfResponseDto getProfessorDetails(Long profSeq) {

        Professor professor = findProfessorById(profSeq);
        RatingBreakdownDto ratingBreakdown = calculateRatings(profSeq);
        Double overallRating = calculateOverallRating(ratingBreakdown);
        ProfessorDto professorDto = createProfessorDto(professor, overallRating, ratingBreakdown);
        List<LectureReviewSummaryDto> lectureReviews = getRecentLectureReviews(profSeq);

        return new ProfResponseDto(professorDto, lectureReviews);
    }

    private Professor findProfessorById(Long profSeq) {
        return professorRepository.findById(profSeq)
                .orElseThrow(() -> new ProfessorNotFoundException(profSeq));
    }

    private RatingBreakdownDto calculateRatings(Long profSeq) {
        // 교수 평가 통계
        Double avgThesisPerf = findAvg(() -> profReviewRepository.findAvgThesisPerf(profSeq));
        Double avgResearchPerf = findAvg(() -> profReviewRepository.findAvgResearchPerf(profSeq));

        // 강의 평가 통계
        Double avgHomework = findAvg(() -> lectureReviewRepository.findAvgHomework(profSeq));
        Double avgLecDifficulty = findAvg(() -> lectureReviewRepository.findAvgLecDifficulty(profSeq));
        Double avgExamDifficulty = findAvg(() -> lectureReviewRepository.findAvgExamDifficulty(profSeq));

        return RatingBreakdownDto.of(
                avgThesisPerf, avgResearchPerf,
                avgHomework, avgLecDifficulty, avgExamDifficulty);
    }

    private Double calculateOverallRating(RatingBreakdownDto ratingBreakdown) {
        return (ratingBreakdown.thesisPerformance() +
                ratingBreakdown.researchPerformance() +
                ratingBreakdown.homework() +
                ratingBreakdown.lectureDifficulty() +
                ratingBreakdown.examDifficulty()) / 5.0;
    }

    private ProfessorDto createProfessorDto(Professor professor, Double overallRating,
            RatingBreakdownDto ratingBreakdown) {
        return ProfessorDto.from(professor, overallRating, ratingBreakdown);
    }


    private List<LectureReviewSummaryDto> getRecentLectureReviews(Long profSeq) {
        List<LectureReivew> recentLectureReviews = lectureReviewRepository
                .findTop3ByLecture_Professor_ProfSeqOrderByCreatedDateDesc(profSeq)
                .stream()
                .limit(3)
                .toList();

        return recentLectureReviews.stream()
                .map(LectureReviewSummaryDto::from)
                .toList();
    }

    private Double findAvg(Supplier<Double> supplier) {
        try {
            Double result = supplier.get();
            return result != null ? result : 0.0;
        } catch (Exception e) {
            return 0.0;
        }
    }
}
