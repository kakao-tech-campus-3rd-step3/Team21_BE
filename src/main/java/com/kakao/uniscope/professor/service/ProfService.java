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

        Professor professor = professorRepository.findById(profSeq)
                .orElseThrow(() -> new ProfessorNotFoundException(profSeq));

        // 교수 평가 통계
        Double avgThesisPerf = findAvg(() -> profReviewRepository.findAvgThesisPerf(profSeq));
        Double avgResearchPerf = findAvg(() -> profReviewRepository.findAvgResearchPerf(profSeq));

        // 강의 평가 통계
        Double avgHomework = findAvg(() -> lectureReviewRepository.findAvgHomework(profSeq));
        Double avgLecDifficulty = findAvg(() -> lectureReviewRepository.findAvgLecDifficulty(profSeq));
        Double avgExamDifficulty = findAvg(() -> lectureReviewRepository.findAvgExamDifficulty(profSeq));

        Double overallRating = (avgThesisPerf + avgResearchPerf +
                avgHomework + avgLecDifficulty + avgExamDifficulty) / 5;

        // 그래프에 평점 나타내기 위한 정보
        RatingBreakdownDto ratingBreakdown = RatingBreakdownDto.of(
                avgThesisPerf, avgResearchPerf,
                avgHomework, avgLecDifficulty, avgExamDifficulty);

        // 교수 dto 반환
        ProfessorDto professorDto = ProfessorDto.from(
                professor, overallRating, ratingBreakdown);

        // 최근 강의평 3개
        List<LectureReivew> recentLectureReviews = lectureReviewRepository
                .findTop3ByLecture_Professor_ProfSeqOrderByCreatedDateDesc(profSeq)
                .stream()
                .limit(3)
                .toList();

        // 강의평 dto 반환
        List<LectureReviewSummaryDto> lectureReviewDtos = recentLectureReviews.stream()
                .map(LectureReviewSummaryDto::from)
                .toList();

        return new ProfResponseDto(professorDto, lectureReviewDtos);
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
