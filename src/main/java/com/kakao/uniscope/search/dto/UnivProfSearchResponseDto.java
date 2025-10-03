package com.kakao.uniscope.search.dto;

import java.util.List;

public record UnivProfSearchResponseDto(
        List<UnivProfSearchResultDto> results,
        int totalCount
) {

}
