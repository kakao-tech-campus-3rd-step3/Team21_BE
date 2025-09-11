package com.kakao.uniscope.univ;

import com.kakao.uniscope.college.dto.CollegeDto;
import com.kakao.uniscope.univ.controller.UnivController;
import com.kakao.uniscope.univ.dto.UnivResponseDto;
import com.kakao.uniscope.univ.dto.UniversityDto;
import com.kakao.uniscope.univ.review.dto.UnivReviewSummaryDto;
import com.kakao.uniscope.univ.service.UnivService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UnivController.class)
public class UnivControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UnivService univService;

    @Test
    @DisplayName("대학교 상세 정보 조회 성공")
    void getUniversityDetails_Success() throws Exception {
        // given
        Long univSeq = 1L;
        UnivResponseDto mockResponseDto = createMockResponseDto();
        when(univService.getUniversityDetails(univSeq)).thenReturn(mockResponseDto);

        // when & then
        mockMvc.perform(get("/api/univ/{univSeq}", univSeq)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.university.univSeq").value(univSeq));
    }

    @Test
    @DisplayName("대학교 상세 정보 조회 실패 - 리소스 없음")
    void getUniversityDetails_NotFound() throws Exception {
        // given: 서비스에서 ResponseStatusException을 던지도록 Mocking
        Long univSeq = 999L;
        when(univService.getUniversityDetails(univSeq)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        // when & then: API 호출 및 404 응답 검증
        mockMvc.perform(get("/api/univ/{univSeq}", univSeq)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    private UnivResponseDto createMockResponseDto() {
        UniversityDto universityDto = new UniversityDto(1L, "충남대학교", "대전", "042", "cnu.ac.kr", "image.png", "1952", 28000);
        List<CollegeDto> collegeDtos = Collections.emptyList();
        List<UnivReviewSummaryDto> reviewDtos = List.of(new UnivReviewSummaryDto(10L, 4, "대학 리뷰", LocalDateTime.now()));
        return new UnivResponseDto(universityDto, collegeDtos, reviewDtos);
    }

}
