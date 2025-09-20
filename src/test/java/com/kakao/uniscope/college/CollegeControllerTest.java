package com.kakao.uniscope.college;

import com.kakao.uniscope.college.controller.CollegeController;
import com.kakao.uniscope.college.dto.CollegeResponseDto;
import com.kakao.uniscope.college.service.CollegeService;
import com.kakao.uniscope.common.exception.ResourceNotFoundException;
import com.kakao.uniscope.department.dto.DepartmentDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CollegeController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class})
public class CollegeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CollegeService collegeService;

    @Test
    @DisplayName("단과대학에 속한 학과 목록 조회 API 호출 성공")
    void getDepartmentsByCollege_Success() throws Exception {

        Long collegeSeq = 1L;
        DepartmentDto mockDepartmentDto = new DepartmentDto(10L, "컴퓨터공학과", "http://cs.ac.kr");
        CollegeResponseDto mockResponseDto = new CollegeResponseDto(List.of(mockDepartmentDto));

        when(collegeService.getDepartmentsByCollege(collegeSeq)).thenReturn(mockResponseDto);

        mockMvc.perform(get("/api/college/{collegeSeq}", collegeSeq)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(collegeService, times(1)).getDepartmentsByCollege(collegeSeq);
    }

    @Test
    @DisplayName("단과대학에 속한 학과 목록 조회 실패 - 리소스 없음")
    void getDepartmentsByCollege_NotFound() throws Exception {
        Long collegeSeq = 999L;
        when(collegeService.getDepartmentsByCollege(collegeSeq)).thenThrow(new ResourceNotFoundException(collegeSeq + "에 해당하는 단과대학을 찾을 수 없습니다."));

        mockMvc.perform(get("/api/college/{collegeSeq}", collegeSeq)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(collegeService, times(1)).getDepartmentsByCollege(collegeSeq);
    }
}
