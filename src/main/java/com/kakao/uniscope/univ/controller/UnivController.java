package com.kakao.uniscope.univ.controller;

import com.kakao.uniscope.college.dto.CollegeListResponseDto;
import com.kakao.uniscope.univ.dto.UnivResponseDto;
import com.kakao.uniscope.univ.review.dto.UnivReviewResponseDto;
import com.kakao.uniscope.univ.review.service.UnivReviewService;
import com.kakao.uniscope.univ.service.UnivService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "대학 API", description = "대학 관련 API 입니다.")
@RestController
@RequestMapping("/api/univ")
public class UnivController {

    private final UnivService univService;
    private final UnivReviewService univReviewService;

    public UnivController(UnivService univService,  UnivReviewService univReviewService) {
        this.univService = univService;
        this.univReviewService = univReviewService;
    }

    @Operation(summary = "대학 정보 조회 API", description = "univSeq에 해당하는 대학의 정보를 조회하는 API입니다.")
    @GetMapping("/{univSeq}")
    public ResponseEntity<UnivResponseDto> getUniversityInfo(@PathVariable Long univSeq) {

        UnivResponseDto responseDto = univService.getUniversityInfo(univSeq);

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @Operation(summary = "대학 리뷰 목록 조회 API", description = "특정 대학의 전체 리뷰 목록을 최신순으로 페이지네이션하여 조회합니다.")
    @GetMapping("/{univSeq}/reviews")
    public ResponseEntity<UnivReviewResponseDto> getAllUnivReviews(
            @Parameter(name = "univSeq", description = "조회할 대학의 고유 번호", example = "1")
            @PathVariable Long univSeq,

            @ParameterObject @PageableDefault(size = 10, sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        UnivReviewResponseDto responseDto = univReviewService.getAllUnivReviews(univSeq, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @Operation(summary = "단과대학 목록 조회 API", description = "특정 대학에 소속된 모든 단과대학 목록과 학과 수를 조회합니다.")
    @GetMapping("/{univSeq}/colleges")
    public ResponseEntity<CollegeListResponseDto> getAllCollegeList(@PathVariable Long univSeq) {
        CollegeListResponseDto responseDto = univService.getAllCollegeList(univSeq);

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
}
