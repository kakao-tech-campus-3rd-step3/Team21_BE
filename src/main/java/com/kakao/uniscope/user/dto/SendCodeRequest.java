package com.kakao.uniscope.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SendCodeRequest(
        @NotBlank @Email String email
) {}


