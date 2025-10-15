package com.kakao.uniscope.search.dto;

import java.util.List;

public record ProfSearchResponseDto(
        List<ProfSearchResultDto> professor,
        int totalCount
) {

}
