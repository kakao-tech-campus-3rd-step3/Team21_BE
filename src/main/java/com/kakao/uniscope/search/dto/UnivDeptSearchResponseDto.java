package com.kakao.uniscope.search.dto;

import java.util.List;

public record UnivDeptSearchResponseDto(
        List<UnivDeptSearchResultDto> results,
        int totalCount
) {

}
