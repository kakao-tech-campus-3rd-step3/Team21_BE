package com.kakao.uniscope.college.controller;

import com.kakao.uniscope.college.dto.CollegeResponseDto;
import com.kakao.uniscope.college.service.CollegeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/college")
public class CollegeController {

    private final CollegeService collegeService;

    public CollegeController(CollegeService collegeService) {
        this.collegeService = collegeService;
    }

    @GetMapping("/{collegeSeq}")
    public ResponseEntity<CollegeResponseDto> getDepartmentsByCollege(@PathVariable Long collegeSeq) {
        CollegeResponseDto responseDto = collegeService.getDepartmentsByCollege(collegeSeq);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
}
