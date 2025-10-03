package com.kakao.uniscope.department;

import com.kakao.uniscope.common.exception.ResourceNotFoundException;
import com.kakao.uniscope.department.controller.DepartmentController;
import com.kakao.uniscope.department.dto.DepartmentInfoResponse;
import com.kakao.uniscope.department.service.DepartmentService;
import com.kakao.uniscope.univ.controller.UnivController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = DepartmentController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class})
public class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DepartmentService departmentService;

    @Test
    void 학과_상세_정보_조회_API_동작_성공() throws Exception {
        Long deptSeq = 1L;

        DepartmentInfoResponse mockResponse = new DepartmentInfoResponse(1L, "", "", "", "", "", "", "", 0, null, null);

        when(departmentService.getDeptDetails(deptSeq)).thenReturn(mockResponse);

        mockMvc.perform(get("/api/departments/{deptSeq}", deptSeq))
                .andExpect(status().isOk());

        verify(departmentService, times(1)).getDeptDetails(deptSeq);
    }

    @Test
    void 학과_상세_정보_조회_API_동작_실패()  throws Exception {
        Long deptSeq = 999L;

        when(departmentService.getDeptDetails(deptSeq))
                .thenThrow(new ResourceNotFoundException(deptSeq + "에 해당하는 학과를 찾을 수 없습니다."));

        mockMvc.perform(get("/api/departments/{deptSeq}", deptSeq))
                .andExpect(status().isNotFound());

        verify(departmentService, times(1)).getDeptDetails(deptSeq);
    }
}
