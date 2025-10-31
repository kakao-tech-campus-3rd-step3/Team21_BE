package com.kakao.uniscope.professor.review.service;

import com.kakao.uniscope.common.exception.ResourceNotFoundException;
import com.kakao.uniscope.professor.entity.Professor;
import com.kakao.uniscope.professor.repository.ProfessorRepository;
import com.kakao.uniscope.professor.review.dto.ProfReviewRequest;
import com.kakao.uniscope.professor.review.dto.ProfReviewResponse;
import com.kakao.uniscope.professor.review.entity.ProfReview;
import com.kakao.uniscope.professor.review.repository.ProfReviewRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProfReviewService {

    private final ProfessorRepository professorRepository;
    private final ProfReviewRepository profReviewRepository;

    @Transactional
    public ProfReviewResponse createReview(ProfReviewRequest request) {

        Professor professor = professorRepository.findById(request.profSeq())
                .orElseThrow(() -> new ResourceNotFoundException(
                        request.profSeq() + "에 해당하는 교수를 찾을 수 없습니다."));

        ProfReview newReview = ProfReview.builder()
                .professor(professor)
                .thesisPerf(request.thesisPerformance())
                .researchPerf(request.researchPerformance())
                .thesisReview(request.reviewText())
                .createDate(LocalDateTime.now())
                .build();

        ProfReview savedReview = profReviewRepository.save(newReview);

        return ProfReviewResponse.of(savedReview.getProfReviewSeq(), savedReview.getCreateDate());
    }
}
