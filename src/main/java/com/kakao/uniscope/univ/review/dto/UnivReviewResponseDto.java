package com.kakao.uniscope.univ.review.dto;

import org.springframework.data.domain.Page;

import java.util.List;

public record UnivReviewResponseDto(
        List<UnivReviewDto> content,
        int pageNumber,
        int pageSize,
        int totalPages,
        long totalElements,
        boolean first,
        boolean last
) {
    public static UnivReviewResponseDto from(Page<UnivReviewDto> reviewPage) {
        return new UnivReviewResponseDto(
                reviewPage.getContent(),
                reviewPage.getNumber(),
                reviewPage.getSize(),
                reviewPage.getTotalPages(),
                reviewPage.getTotalElements(),
                reviewPage.isFirst(),
                reviewPage.isLast()
        );
    }
}
