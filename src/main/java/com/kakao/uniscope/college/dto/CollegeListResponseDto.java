package com.kakao.uniscope.college.dto;

import java.util.List;

public record CollegeListResponseDto(
        List<CollegeSummaryDto> colleges
) { }
