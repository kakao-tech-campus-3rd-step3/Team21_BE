package com.kakao.uniscope.comparison.controller;

import com.kakao.uniscope.comparison.dto.ProfessorComparisonDto;
import com.kakao.uniscope.comparison.dto.UniversityComparisonDto;
import com.kakao.uniscope.comparison.service.ComparisonService;
import com.kakao.uniscope.comparison.service.ProfessorComparisonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "비교 API", description = "대학, 교수 비교 관련 API입니다.")
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

    @Operation(summary = "학교 비교 데이터 조회 API", description = "1~2개 학교의 간단한 정보와 각 평가 항목의 평균을 반환합니다.")
    @GetMapping("/universities")
    public ResponseEntity<List<UniversityComparisonDto>> getUniversitiesComparisonData(
            @Parameter(
                    description = "비교할 대학들의 고유 번호 목록 (최대 2개)",
                    schema = @Schema(type = "array", example = "1,2")
            )
            @RequestParam("univSeqs") List<Long> univSeqs
    ) {
        List<UniversityComparisonDto> response = comparisonService.getUniversitiesComparisonData(univSeqs);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "교수 비교 데이터 조회 API", description = "1~3명의 교수의 간단한 정보와 각 평가 항목의 평균을 반환합니다.")
    @GetMapping("/professors")
    public ResponseEntity<List<ProfessorComparisonDto>> getProfessorsComparisonData(
            @Parameter(
                    description = "비교할 교수들의 고유 번호 목록 (최대 3개)",
                    schema = @Schema(type = "array", example = "1,2,3")
            )
            @RequestParam("profSeqs") List<Long> profSeqs
    ) {
        List<ProfessorComparisonDto> response = professorComparisonService.getProfessorsComparisonData(profSeqs);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
