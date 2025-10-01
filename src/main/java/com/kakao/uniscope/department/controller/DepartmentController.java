package com.kakao.uniscope.department.controller;

import com.kakao.uniscope.department.dto.DepartmentInfoResponse;
import com.kakao.uniscope.department.service.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/{deptSeq}")
    public ResponseEntity<DepartmentInfoResponse> getDeptDetails(@PathVariable Long deptSeq) {
        DepartmentInfoResponse response = departmentService.getDeptDetails(deptSeq);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
