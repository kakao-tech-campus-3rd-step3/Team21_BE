package com.kakao.uniscope.professor.controller;

import com.kakao.uniscope.professor.dto.LectureReviewPageResponseDto;
import com.kakao.uniscope.professor.dto.ProfResponseDto;
import com.kakao.uniscope.professor.service.ProfService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/prof")
@RequiredArgsConstructor
public class ProfController {

    private final ProfService profService;

    @GetMapping("/{prof_seq}")
    public ResponseEntity<ProfResponseDto> getProfessorDetails(@PathVariable("prof_seq") Long profSeq) {
        ProfResponseDto response = profService.getProfessorDetails(profSeq);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{prof_seq}/reviews")
    public ResponseEntity<LectureReviewPageResponseDto> getProfessorLectureReviews(
            @PathVariable("prof_seq") Long profSeq,
            @PageableDefault(size = 10, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {

        LectureReviewPageResponseDto response = profService.getProfessorLectureReviews(profSeq, pageable);
        return ResponseEntity.ok(response);
    }
}
