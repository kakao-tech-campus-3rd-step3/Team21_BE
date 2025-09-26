package com.kakao.uniscope.univ.controller;

import com.kakao.uniscope.college.dto.CollegeListResponseDto;
import com.kakao.uniscope.univ.dto.UnivResponseDto;
import com.kakao.uniscope.univ.review.dto.UnivReviewResponseDto;
import com.kakao.uniscope.univ.review.service.UnivReviewService;
import com.kakao.uniscope.univ.service.UnivService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/univ")
public class UnivController {

    private final UnivService univService;
    private final UnivReviewService univReviewService;

    public UnivController(UnivService univService,  UnivReviewService univReviewService) {
        this.univService = univService;
        this.univReviewService = univReviewService;
    }

    @GetMapping("/{univSeq}")
    public ResponseEntity<UnivResponseDto> getUniversityInfo(@PathVariable Long univSeq) {

        UnivResponseDto responseDto = univService.getUniversityInfo(univSeq);

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @GetMapping("/{univSeq}/reviews")
    public ResponseEntity<UnivReviewResponseDto> getAllUnivReviews(
            @PathVariable Long univSeq,
            @PageableDefault(size = 10, sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        UnivReviewResponseDto responseDto = univReviewService.getAllUnivReviews(univSeq, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    // 대학의 모든 단과대학 목록을 반환하는 API
    @GetMapping("/{univSeq}/colleges")
    public ResponseEntity<CollegeListResponseDto> getAllCollegeList(@PathVariable Long univSeq) {
        CollegeListResponseDto responseDto = univService.getAllCollegeList(univSeq);

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
}
