package com.kakao.uniscope.univ.univReview;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakao.uniscope.common.exception.ResourceNotFoundException;
import com.kakao.uniscope.univ.review.controller.UnivReviewController;
import com.kakao.uniscope.univ.review.dto.ReviewCreateResponse;
import com.kakao.uniscope.univ.review.dto.UnivReviewRequest;
import com.kakao.uniscope.univ.review.service.UnivReviewService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.Mockito.*;

@WebMvcTest(controllers = UnivReviewController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class})
public class UnivReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UnivReviewService univReviewService;

    @Test
    void 대학_리뷰_작성_성공_201_Created() throws Exception {
        UnivReviewRequest request = createRequestDto(1L);

        Long newReviewSeq = 1L;
        ReviewCreateResponse mockResponse = ReviewCreateResponse.of(newReviewSeq);

        when(univReviewService.createReview(eq(request))).thenReturn(mockResponse);

        mockMvc.perform(post("/api/reviews/univ")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.reviewSeq").value(newReviewSeq));

        verify(univReviewService, times(1)).createReview(eq(request));
    }

    @Test
    void 리뷰_작성_실패_대학을_찾을_수_없음() throws Exception {
        Long notExistUnivSeq = 999L;
        UnivReviewRequest request = createRequestDto(notExistUnivSeq);

        when(univReviewService.createReview(eq(request))).thenThrow(new ResourceNotFoundException(notExistUnivSeq + "에 해당하는 대학교를 찾을 수 없습니다."));

        mockMvc.perform(post("/api/reviews/univ")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());

        verify(univReviewService, times(1)).createReview(eq(request));
    }

    private UnivReviewRequest createRequestDto(Long univSeq) {
        return new UnivReviewRequest(
                univSeq, 5, 4, 3, 4, 5, "유효한 리뷰 텍스트."
        );
    }
}
