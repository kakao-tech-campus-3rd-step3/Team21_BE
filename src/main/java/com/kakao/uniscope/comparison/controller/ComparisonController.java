package com.kakao.uniscope.comparison.controller;

import com.kakao.uniscope.comparison.dto.UniversityComparisonDto;
import com.kakao.uniscope.comparison.service.ComparisonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/comparison")
public class ComparisonController {

    private final ComparisonService comparisonService;

    public ComparisonController(ComparisonService comparisonService) {
        this.comparisonService = comparisonService;
    }

    // 학교 비교 데이터 조회 API
    @GetMapping("/universities")
    public ResponseEntity<List<UniversityComparisonDto>> getUniversitiesComparisonData(
            @RequestParam("univSeqs") List<Long> univSeqs
    ) {
        List<UniversityComparisonDto> response = comparisonService.getUniversitiesComparisonData(univSeqs);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
