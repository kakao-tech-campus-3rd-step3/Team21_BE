package com.kakao.uniscope.search.dto;

import java.util.List;

public record UnivSearchResponseDto(
        List<UnivSearchResultDto> universities,
        int totalCount
) {

}
