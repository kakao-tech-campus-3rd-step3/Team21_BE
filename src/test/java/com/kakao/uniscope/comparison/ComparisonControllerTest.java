package com.kakao.uniscope.comparison;


import com.kakao.uniscope.common.exception.ResourceNotFoundException;
import com.kakao.uniscope.comparison.controller.ComparisonController;
import com.kakao.uniscope.comparison.dto.UniversityComparisonDto;
import com.kakao.uniscope.comparison.exception.ComparisonException;
import com.kakao.uniscope.comparison.service.ComparisonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.*;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ComparisonController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class})
public class ComparisonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ComparisonService comparisonService;

    @Test
    void 대학_2개_비교_API_호출_성공() throws Exception {

        List<Long> univSeqs = List.of(1L, 2L);
        List<UniversityComparisonDto> mockResponse = TestComparisonDataFactory.createMockComparisonData();

        when(comparisonService.getUniversitiesComparisonData(univSeqs)).thenReturn(mockResponse);

        mockMvc.perform(get("/api/comparison/universities")
                        .param("univSeqs", "1,2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(comparisonService, times(1)).getUniversitiesComparisonData(univSeqs);
    }

    @Test
    void 대학이_1개만_있어도_API_호출_성공() throws Exception {

        Long singleUnivSeq = 1L;
        List<Long> univSeqs = List.of(singleUnivSeq);

        List<UniversityComparisonDto> mockData = List.of(TestComparisonDataFactory.createMockComparisonData().getFirst());

        when(comparisonService.getUniversitiesComparisonData(univSeqs)).thenReturn(mockData);

        mockMvc.perform(get("/api/comparison/universities")
                        .param("univSeqs", singleUnivSeq.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));

        verify(comparisonService, times(1)).getUniversitiesComparisonData(univSeqs);
    }

    @Test
    void 요청된_대학_정보가_없으면_404_Not_Found_반환() throws Exception {

        List<Long> univSeqs = List.of(999L);

        when(comparisonService.getUniversitiesComparisonData(univSeqs))
                .thenThrow(new ResourceNotFoundException("요청된 일부 대학 정보를 찾을 수 없습니다."));

        mockMvc.perform(get("/api/comparison/universities")
                        .param("univSeqs", "999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(comparisonService, times(1)).getUniversitiesComparisonData(univSeqs);
    }

    @Test
    void 쿼리_파라미터_없이_요청했을때_400_Bad_Request_반환()  throws Exception {

        List<Long> emptyList = Collections.emptyList();

        when(comparisonService.getUniversitiesComparisonData(emptyList))
                .thenThrow(new ComparisonException("비교할 대학 ID가 필요합니다."));

        mockMvc.perform(get("/api/comparison/universities")
                        .param("univSeqs", "")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(comparisonService, times(1)).getUniversitiesComparisonData(emptyList);
    }


}
