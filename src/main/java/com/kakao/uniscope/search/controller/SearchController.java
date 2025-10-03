package com.kakao.uniscope.search.controller;

import com.kakao.uniscope.search.dto.DeptSearchResponseDto;
import com.kakao.uniscope.search.dto.ProfSearchResponseDto;
import com.kakao.uniscope.search.dto.UnivDeptSearchResponseDto;
import com.kakao.uniscope.search.dto.UnivProfSearchResponseDto;
import com.kakao.uniscope.search.dto.UnivSearchResponseDto;
import com.kakao.uniscope.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    // 1. 대학 검색
    @GetMapping("/univ")
    public ResponseEntity<UnivSearchResponseDto> searchUniversity(
            @RequestParam String keyword,
            @PageableDefault Pageable pageable
    ) {
        UnivSearchResponseDto response = searchService.searchUniversity(keyword, pageable);
        return ResponseEntity.ok(response);
    }

    // 2. 교수 검색
    @GetMapping("/prof")
    public ResponseEntity<ProfSearchResponseDto> searchProfessor(
            @RequestParam String keyword,
            @PageableDefault Pageable pageable
    ) {
        ProfSearchResponseDto response = searchService.searchProfessor(keyword, pageable);
        return ResponseEntity.ok(response);
    }

    // 3. 학과 검색
    @GetMapping("/dept")
    public ResponseEntity<DeptSearchResponseDto> searchDepartment(
            @RequestParam String keyword,
            @PageableDefault Pageable pageable
    ) {
        DeptSearchResponseDto response = searchService.searchDepartment(keyword, pageable);
        return ResponseEntity.ok(response);
    }

    // 4. 대학 + 학과 검색
    @GetMapping("/univ-dept")
    public ResponseEntity<UnivDeptSearchResponseDto> searchUnivAndDept(
            @RequestParam String univKeyword,
            @RequestParam String deptKeyword,
            @PageableDefault Pageable pageable
    ) {
        UnivDeptSearchResponseDto response = searchService.searchUnivAndDept(
                univKeyword, deptKeyword, pageable
        );
        return ResponseEntity.ok(response);
    }

    // 5. 대학 + 교수 검색
    @GetMapping("/univ-prof")
    public ResponseEntity<UnivProfSearchResponseDto> searchUnivAndProf(
            @RequestParam String univKeyword,
            @RequestParam String profKeyword,
            @PageableDefault Pageable pageable
    ) {
        UnivProfSearchResponseDto response = searchService.searchUnivAndProf(
                univKeyword, profKeyword, pageable
        );
        return ResponseEntity.ok(response);
    }
}
