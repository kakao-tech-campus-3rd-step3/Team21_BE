package com.kakao.uniscope.comparison.service;

import com.kakao.uniscope.comparison.dto.ProfessorComparisonDto;
import com.kakao.uniscope.comparison.dto.ProfessorScoreDto;
import com.kakao.uniscope.comparison.dto.SemesterDto;
import com.kakao.uniscope.lecture.review.repository.LectureReviewRepository;
import com.kakao.uniscope.professor.entity.Professor;
import com.kakao.uniscope.professor.review.repository.ProfReviewRepository;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProfessorComparisonMapper {

    private final ProfReviewRepository profReviewRepository;
    private final LectureReviewRepository lectureReviewRepository;


    public ProfessorComparisonDto toComparisonDto(Professor professor) {
        ProfessorScoreDto scores = calculateScores(professor.getProfSeq());
        List<SemesterDto> semesterTrends = calculateSemesterTrends(professor.getProfSeq());

        return ProfessorComparisonDto.builder()
                .profSeq(professor.getProfSeq())
                .profName(professor.getProfName())
                .univName(professor.getDepartment().getCollege().getUniversity().getName())
                .deptName(professor.getDepartment().getDeptName())
                .scores(scores)
                .semesterDto(semesterTrends)
                .build();
    }

    private ProfessorScoreDto calculateScores(Long profSeq) {
        Double avgThesisPerf = findAvg(() -> profReviewRepository.findAvgThesisPerf(profSeq));
        Double avgResearchPerf = findAvg(() -> profReviewRepository.findAvgResearchPerf(profSeq));
        Double avgHomework = findAvg(() -> lectureReviewRepository.findAvgHomework(profSeq));
        Double avgLecDifficulty = findAvg(() -> lectureReviewRepository.findAvgLecDifficulty(profSeq));
        Double avgExamDifficulty = findAvg(() -> lectureReviewRepository.findAvgExamDifficulty(profSeq));

        return new ProfessorScoreDto(
                avgThesisPerf,
                avgResearchPerf,
                avgHomework,
                avgLecDifficulty,
                avgExamDifficulty
        );
    }

    private List<SemesterDto> calculateSemesterTrends(Long profSeq) {
        List<Object[]> semesterAverages = lectureReviewRepository
                .findSemesterAveragesByProfessor(profSeq);

        return semesterAverages.stream()
                .map(this::mapToSemesterTrendDto)
                .collect(Collectors.toList());
    }

    private SemesterDto mapToSemesterTrendDto(Object[] row) {
        String dbSemester = (String) row[0];  // "2024-1", "2023-2" 등
        Double avgRating = (Double) row[1];

        String displaySemester = convertToDisplaySemester(dbSemester);
        return SemesterDto.of(displaySemester, avgRating);
    }

    private String convertToDisplaySemester(String dbSemester) {
        String[] parts = dbSemester.split("-");
        int year = Integer.parseInt(parts[0]);
        String semester = parts[1];

        int displayYear = year - 2020;  // 2021 -> 1년, 2022 -> 2년, 2023 -> 3년, 2024 -> 4년
        return displayYear + "학년 " + semester + "학기";
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
