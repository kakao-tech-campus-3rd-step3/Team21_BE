package com.kakao.uniscope.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
public class UserLoginRequestDto {

    @NotBlank(message = "아이디는 필수입니다")
    private String userId;

    @NotBlank(message = "비밀번호는 필수입니다")
    private String userPwd;

    public UserLoginRequestDto(String userId, String userPwd) {
        this.userId = userId;
        this.userPwd = userPwd;
    }
}