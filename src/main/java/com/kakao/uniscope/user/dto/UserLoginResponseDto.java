package com.kakao.uniscope.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserLoginResponseDto {

    private String accessToken;
    private String tokenType = "Bearer";
    private Long userSeq;
    private String userId;

    @Builder
    public UserLoginResponseDto(String accessToken, Long userSeq, String userId) {
        this.accessToken = accessToken;
        this.userSeq = userSeq;
        this.userId = userId;
    }
}