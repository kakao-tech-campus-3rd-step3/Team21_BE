package com.kakao.uniscope.comparison.controller;

import com.kakao.uniscope.comparison.dto.ProfessorComparisonDto;
import com.kakao.uniscope.comparison.dto.UniversityComparisonDto;
import com.kakao.uniscope.comparison.service.ComparisonService;
import com.kakao.uniscope.comparison.service.ProfessorComparisonService;
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
    private final ProfessorComparisonService professorComparisonService;

    public ComparisonController(ComparisonService comparisonService,
            ProfessorComparisonService professorComparisonService) {
        this.comparisonService = comparisonService;
        this.professorComparisonService = professorComparisonService;
    }

    // 학교 비교 데이터 조회 API
    @GetMapping("/universities")
    public ResponseEntity<List<UniversityComparisonDto>> getUniversitiesComparisonData(
            @RequestParam("univSeqs") List<Long> univSeqs
    ) {
        List<UniversityComparisonDto> response = comparisonService.getUniversitiesComparisonData(univSeqs);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 교수 비교 데이터 조회 API
    @GetMapping("/professors")
    public ResponseEntity<List<ProfessorComparisonDto>> getProfessorsComparisonData(
            @RequestParam("profSeqs") List<Long> profSeqs
    ) {
        List<ProfessorComparisonDto> response = professorComparisonService.getProfessorsComparisonData(profSeqs);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
