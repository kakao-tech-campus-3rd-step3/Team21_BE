package com.kakao.uniscope.department.controller;

import com.kakao.uniscope.department.dto.DepartmentInfoResponse;
import com.kakao.uniscope.department.service.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "학과 API", description = "학과 관련 API 입니다.")
@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @Operation(summary = "학과 정보 조회 API", description = "deptSeq에 해당하는 학과의 정보를 반환합니다.")
    @GetMapping("/{deptSeq}")
    public ResponseEntity<DepartmentInfoResponse> getDeptDetails(@PathVariable Long deptSeq) {
        DepartmentInfoResponse response = departmentService.getDeptDetails(deptSeq);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
