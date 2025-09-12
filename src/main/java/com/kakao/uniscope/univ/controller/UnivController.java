package com.kakao.uniscope.univ.controller;

import com.kakao.uniscope.univ.dto.UnivResponseDto;
import com.kakao.uniscope.univ.service.UnivService;
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

    public UnivController(UnivService univService) {
        this.univService = univService;
    }

    @GetMapping("/{univSeq}")
    public ResponseEntity<UnivResponseDto> getUniversityDetails(@PathVariable Long univSeq) {

        UnivResponseDto responseDto = univService.getUniversityDetails(univSeq);

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
}
