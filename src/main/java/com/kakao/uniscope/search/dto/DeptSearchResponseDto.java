package com.kakao.uniscope.search.dto;

import java.util.List;

public record DeptSearchResponseDto(
        List<DeptSearchResultDto> departments,
        int totalCount
) {

}
