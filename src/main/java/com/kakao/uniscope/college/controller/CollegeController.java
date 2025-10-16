package com.kakao.uniscope.college.controller;

import com.kakao.uniscope.college.dto.CollegeDetailsResponseDto;
import com.kakao.uniscope.college.dto.DepartmentsByCollegeResponseDto;
import com.kakao.uniscope.college.service.CollegeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "단과대학 API", description = "단과대학에 관련된 API 입니다.")
@RestController
@RequestMapping("/api/college")
public class CollegeController {

    private final CollegeService collegeService;

    public CollegeController(CollegeService collegeService) {
        this.collegeService = collegeService;
    }

    @Operation(summary = "단과대학의 학과 목록 조회 API", description = "collegeSeq에 해당하는 단과대학에 속한 학과 목록을 반환합니다.")
    @GetMapping("/{collegeSeq}")
    public ResponseEntity<DepartmentsByCollegeResponseDto> getDepartmentsByCollege(@PathVariable Long collegeSeq) {
        DepartmentsByCollegeResponseDto responseDto = collegeService.getDepartmentsByCollege(collegeSeq);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @Operation(summary = "단과대학의 정보 조회 API", description = "collegeSeq에 해당하는 단과대학의 정보를 반환합니다.")
    @GetMapping("/{collegeSeq}/details")
    public ResponseEntity<CollegeDetailsResponseDto> getCollegeDetails(@PathVariable Long collegeSeq) {
        CollegeDetailsResponseDto responseDto = collegeService.getCollegeDetails(collegeSeq);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
}
