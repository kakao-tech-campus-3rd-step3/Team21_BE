package com.kakao.uniscope.professor.controller;

import com.kakao.uniscope.professor.dto.LectureReviewPageResponseDto;
import com.kakao.uniscope.professor.dto.ProfResponseDto;
import com.kakao.uniscope.professor.service.ProfService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "교수 API", description = "교수 관련 API 입니다.")
@RestController
@RequestMapping("/api/prof")
@RequiredArgsConstructor
public class ProfController {

    private final ProfService profService;

    @Operation(summary = "교수 정보 조회 API", description = "특정 교수의 상세 정보와 담당 강의, 교수 리뷰를 조회하는 API 입니다.")
    @GetMapping("/{prof_seq}")
    public ResponseEntity<ProfResponseDto> getProfessorDetails(@PathVariable("prof_seq") Long profSeq) {
        ProfResponseDto response = profService.getProfessorDetails(profSeq);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "교수의 강의 목록 조회 API", description = "특정 교수의 전체 강의 목록을 최신순으로 페이지네이션하여 조회합니다.")
    @GetMapping("/{prof_seq}/reviews")
    public ResponseEntity<LectureReviewPageResponseDto> getProfessorLectureReviews(
            @PathVariable("prof_seq") Long profSeq,
            @ParameterObject @PageableDefault(size = 10, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {

        LectureReviewPageResponseDto response = profService.getProfessorLectureReviews(profSeq, pageable);
        return ResponseEntity.ok(response);
    }
}
