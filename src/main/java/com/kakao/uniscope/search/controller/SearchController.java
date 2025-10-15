package com.kakao.uniscope.search.controller;

import com.kakao.uniscope.search.dto.DeptSearchResponseDto;
import com.kakao.uniscope.search.dto.ProfSearchResponseDto;
import com.kakao.uniscope.search.dto.UnivDeptSearchResponseDto;
import com.kakao.uniscope.search.dto.UnivProfSearchResponseDto;
import com.kakao.uniscope.search.dto.UnivSearchResponseDto;
import com.kakao.uniscope.search.service.SearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "통합 검색 API", description = "대학명, 학과명, 교수명 등을 조합하여 정보를 검색합니다.")
@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @Operation(summary = "단일 대학 검색", description = "키워드를 포함하는 대학 목록을 검색하고 페이지네이션하여 반환합니다.")
    @GetMapping("/univ")
    public ResponseEntity<UnivSearchResponseDto> searchUniversity(
            @RequestParam String keyword,
            @ParameterObject @PageableDefault Pageable pageable
    ) {
        UnivSearchResponseDto response = searchService.searchUniversity(keyword, pageable);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "단일 교수 검색", description = "키워드를 포함하는 교수 목록을 검색하고 페이지네이션하여 반환합니다.")
    @GetMapping("/prof")
    public ResponseEntity<ProfSearchResponseDto> searchProfessor(
            @RequestParam String keyword,
            @ParameterObject @PageableDefault Pageable pageable
    ) {
        ProfSearchResponseDto response = searchService.searchProfessor(keyword, pageable);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "단일 학과 검색", description = "키워드를 포함하는 학과 목록을 검색하고 페이지네이션하여 반환합니다.")
    @GetMapping("/dept")
    public ResponseEntity<DeptSearchResponseDto> searchDepartment(
            @RequestParam String keyword,
            @ParameterObject @PageableDefault Pageable pageable
    ) {
        DeptSearchResponseDto response = searchService.searchDepartment(keyword, pageable);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "대학 + 학과 복합 검색", description = "특정 대학 내에서 학과명을 검색합니다.")
    @GetMapping("/univ-dept")
    public ResponseEntity<UnivDeptSearchResponseDto> searchUnivAndDept(
            @RequestParam String univKeyword,
            @RequestParam String deptKeyword,
            @ParameterObject @PageableDefault Pageable pageable
    ) {
        UnivDeptSearchResponseDto response = searchService.searchUnivAndDept(
                univKeyword, deptKeyword, pageable
        );
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "대학 + 교수 복합 검색", description = "특정 대학 내에서 교수명을 검색합니다.")
    @GetMapping("/univ-prof")
    public ResponseEntity<UnivProfSearchResponseDto> searchUnivAndProf(
            @RequestParam String univKeyword,
            @RequestParam String profKeyword,
            @ParameterObject @PageableDefault Pageable pageable
    ) {
        UnivProfSearchResponseDto response = searchService.searchUnivAndProf(
                univKeyword, profKeyword, pageable
        );
        return ResponseEntity.ok(response);
    }
}
