package com.kakao.uniscope.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommonResponseDto<T> {

    private String message;
    private T data;

    @Builder
    public CommonResponseDto(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public static <T> CommonResponseDto<T> ok(String message, T data) {
        return CommonResponseDto.<T>builder()
                .message(message)
                .data(data)
                .build();
    }

    public static <T> CommonResponseDto<T> ok(String message) {
        return CommonResponseDto.<T>builder()
                .message(message)
                .data(null)
                .build();
    }

    public static <T> CommonResponseDto<T> error(String message) {
        return CommonResponseDto.<T>builder()
                .message(message)
                .data(null)
                .build();
    }
}