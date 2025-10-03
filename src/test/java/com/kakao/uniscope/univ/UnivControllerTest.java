package com.kakao.uniscope.univ;

import com.kakao.uniscope.college.dto.CollegeListResponseDto;
import com.kakao.uniscope.common.exception.ResourceNotFoundException;
import com.kakao.uniscope.univ.controller.UnivController;
import com.kakao.uniscope.univ.dto.UnivResponseDto;
import com.kakao.uniscope.univ.dto.UniversityDto;
import com.kakao.uniscope.univ.review.dto.UnivReviewResponseDto;
import com.kakao.uniscope.univ.review.service.UnivReviewService;
import com.kakao.uniscope.univ.service.UnivService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UnivController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class})
public class UnivControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UnivService univService;

    @MockitoBean
    private UnivReviewService univReviewService;

    @Test
    @DisplayName("대학교 상세 정보 조회 성공")
    void getUniversityDetails_Success() throws Exception {
        Long univSeq = 1L;
        UnivResponseDto mockResponseDto = createMockResponseDto();
        when(univService.getUniversityInfo(univSeq)).thenReturn(mockResponseDto);

        mockMvc.perform(get("/api/univ/{univSeq}", univSeq)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.university.univSeq").value(univSeq));
    }

    @Test
    @DisplayName("대학교 상세 정보 조회 실패 - 리소스 없음")
    void getUniversityDetails_NotFound() throws Exception {
        Long univSeq = 999L;
        when(univService.getUniversityInfo(univSeq)).thenThrow(new ResourceNotFoundException(univSeq + "에 해당하는 대학교를 찾을 수 없습니다."));

        mockMvc.perform(get("/api/univ/{univSeq}", univSeq)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void 대학_리뷰_목록_조회_성공() throws Exception {
        Long univSeq = 1L;
        UnivReviewResponseDto mockResponseDto = new UnivReviewResponseDto(Collections.emptyList(), 0, 10, 0, 0, true, true);

        when(univReviewService.getAllUnivReviews(eq(univSeq), any(Pageable.class))).thenReturn(mockResponseDto);

        mockMvc.perform(get("/api/univ/{univSeq}/reviews", univSeq)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(univReviewService, times(1)).getAllUnivReviews(eq(univSeq), any(Pageable.class));
    }

    @Test
    void 대학이_없는_경우_대학_리뷰_목록_조회_실패() throws Exception {
        Long univSeq = 999L;
        when(univReviewService.getAllUnivReviews(eq(univSeq), any(Pageable.class)))
                .thenThrow(new ResourceNotFoundException(univSeq + "에 해당하는 대학교를 찾을 수 없습니다."));

        mockMvc.perform(get("/api/univ/{univSeq}/reviews", univSeq)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(univReviewService, times(1)).getAllUnivReviews(eq(univSeq), any(Pageable.class));
    }

    @Test
    void 대학의_단과대학_목록_조회_성공() throws Exception {
        Long univSeq = 1L;
        CollegeListResponseDto mockResponseDto = new CollegeListResponseDto(Collections.emptyList());

        when(univService.getAllCollegeList(eq(univSeq))).thenReturn(mockResponseDto);

        mockMvc.perform(get("/api/univ/{univSeq}/colleges", univSeq)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(univService, times(1)).getAllCollegeList(eq(univSeq));
    }

    @Test
    void 존재하지_않는_대학의_단과대학_목록_조회_시_실패() throws Exception {
        Long univSeq = 999L;

        when(univService.getAllCollegeList(eq(univSeq)))
                .thenThrow(new ResourceNotFoundException(univSeq + "에 해당하는 대학교를 찾을 수 없습니다."));

        mockMvc.perform(get("/api/univ/{univSeq}/colleges", univSeq)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(univService, times(1)).getAllCollegeList(eq(univSeq));
    }

    private UnivResponseDto createMockResponseDto() {
        UniversityDto universityDto = new UniversityDto(1L, "충남대학교", "대전", "042", "cnu.ac.kr", "image.png", "1952", 28000, 1, 17, 80, 3.5, 101L);
        return new UnivResponseDto(universityDto);
    }
}
