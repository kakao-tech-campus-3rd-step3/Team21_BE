package com.kakao.uniscope.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
public class UserSignupRequestDto {

    @NotBlank(message = "이메일은 필수입니다")
    @Email(message = "올바른 이메일 형식이 아닙니다")
    private String userEmail;

    @NotBlank(message = "아이디는 필수입니다")
    @Size(min = 3, max = 20, message = "아이디는 3자 이상 20자 이하여야 합니다")
    @Pattern(regexp = "^[A-Za-z0-9._-]+$", message = "아이디는 영문/숫자/._- 만 사용할 수 있습니다")
    private String userId;

    @NotBlank(message = "비밀번호는 필수입니다")
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하여야 합니다")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-={}\\[\\]|;:'\",.<>/?`~]).{8,20}$",
            message = "비밀번호는 영문/숫자/특수문자를 모두 포함해야 합니다"
    )
    private String userPwd;

    public UserSignupRequestDto(String userEmail, String userId, String userPwd) {
        this.userEmail = userEmail;
        this.userId = userId;
        this.userPwd = userPwd;
    }
}