package com.kakao.uniscope.comparison.service;

import com.kakao.uniscope.common.exception.ResourceNotFoundException;
import com.kakao.uniscope.comparison.dto.ProfessorComparisonDto;
import com.kakao.uniscope.comparison.dto.ProfessorScoreDto;
import com.kakao.uniscope.comparison.exception.ComparisonException;
import com.kakao.uniscope.lecture.review.repository.LectureReviewRepository;
import com.kakao.uniscope.professor.entity.Professor;
import com.kakao.uniscope.professor.repository.ProfessorRepository;
import com.kakao.uniscope.professor.review.repository.ProfReviewRepository;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProfessorComparisonService {

    private final ProfessorRepository professorRepository;
    private final ProfReviewRepository profReviewRepository;
    private final LectureReviewRepository lectureReviewRepository;

    public List<ProfessorComparisonDto> getProfessorsComparisonData(List<Long> profSeqs) {
        if (profSeqs == null || profSeqs.isEmpty() || profSeqs.size() > 3) {
            throw new ComparisonException("비교할 교수 ID는 1개 이상 3개 이하로 제공되어야 합니다.");
        }

        List<Professor> professors = professorRepository.findAllById(profSeqs);

        if (professors.size() != profSeqs.size()) {
            throw new ResourceNotFoundException("요청된 일부 교수 정보를 찾을 수 없거나 존재하지 않습니다.");
        }

        return professors.stream()
                .map(this::toProfessorComparisonDto)
                .collect(Collectors.toList());
    }

    private ProfessorComparisonDto toProfessorComparisonDto(Professor prof) {
        ProfessorScoreDto scores = calculateScores(prof.getProfSeq());

        return ProfessorComparisonDto.builder()
                .profSeq(prof.getProfSeq())
                .profName(prof.getProfName())
                .univName(prof.getDepartment().getCollege().getUniversity().getName())
                .deptName(prof.getDepartment().getDeptName())
                .scores(scores)
                .build();
    }

    private ProfessorScoreDto calculateScores(Long profSeq) {
        // 교수 평가 통계
        Double avgThesisPerf = findAvg(() -> profReviewRepository.findAvgThesisPerf(profSeq));
        Double avgResearchPerf = findAvg(() -> profReviewRepository.findAvgResearchPerf(profSeq));

        // 강의 평가 통계
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

    private Double findAvg(Supplier<Double> supplier) {
        try {
            Double result = supplier.get();
            return result != null ? result : 0.0;
        } catch (Exception e) {
            return 0.0;
        }
    }
}
