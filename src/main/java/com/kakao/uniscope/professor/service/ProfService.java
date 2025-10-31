package com.kakao.uniscope.professor.service;

import com.kakao.uniscope.lecture.review.dto.LectureReviewSummaryDto;
import com.kakao.uniscope.lecture.review.entity.LectureReview;
import com.kakao.uniscope.lecture.review.repository.LectureReviewRepository;
import com.kakao.uniscope.professor.dto.DepartmentAverageDto;
import com.kakao.uniscope.professor.dto.LectureReviewPageResponseDto;
import com.kakao.uniscope.professor.dto.LectureSimpleDto;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        DepartmentAverageDto departmentAverage = calculateDeptRatings(professor.getDepartment().getDeptSeq());
        Double overallRating = lectureReviewRepository.findOverallLectureRating(profSeq);

        List<LectureSimpleDto> lectures = professor.getLectures().stream()
                .map(LectureSimpleDto::from)
                .toList();

        Integer totalReviewCount = lectureReviewRepository.countByProfessorId(profSeq);

        ProfessorDto professorDto = ProfessorDto.from(professor, overallRating, ratingBreakdown, departmentAverage, lectures, totalReviewCount);
        List<LectureReviewSummaryDto> lectureReviews = getRecentLectureReviews(profSeq);

        return new ProfResponseDto(professorDto, lectureReviews);
    }

    public LectureReviewPageResponseDto getProfessorLectureReviews(Long profSeq, Pageable pageable) {
        findProfessorById(profSeq);

        Page<LectureReview> reviewPage = lectureReviewRepository
                .findByLecture_Professor_ProfSeqOrderByCreatedDateDesc(profSeq, pageable);

        Page<LectureReviewSummaryDto> dtoPage = reviewPage.map(LectureReviewSummaryDto::from);

        return LectureReviewPageResponseDto.from(dtoPage);
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

    private DepartmentAverageDto calculateDeptRatings(Long deptSeq) {
        // 학과 교수 평가 통계
        Double avgDeptThesisPerf = findAvg(() -> profReviewRepository.findDeptAvgThesisPerf(deptSeq));
        Double avgDeptResearchPerf = findAvg(() -> profReviewRepository.findDeptAvgResearchPerf(deptSeq));

        // 학과 강의 평가 통계
        Double avgDeptHomework = findAvg(() -> lectureReviewRepository.findDeptAvgHomework(deptSeq));
        Double avgDeptLecDifficulty = findAvg(() -> lectureReviewRepository.findDeptAvgLecDifficulty(deptSeq));
        Double avgDeptExamDifficulty = findAvg(() -> lectureReviewRepository.findDeptAvgExamDifficulty(deptSeq));

        return DepartmentAverageDto.of(
                avgDeptThesisPerf, avgDeptResearchPerf,
                avgDeptHomework,avgDeptLecDifficulty, avgDeptExamDifficulty);
    }

    private List<LectureReviewSummaryDto> getRecentLectureReviews(Long profSeq) {
        List<LectureReview> recentLectureReviews = lectureReviewRepository
                .findTop3ByLecture_Professor_ProfSeqOrderByCreatedDateDesc(profSeq)
                .stream()
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
