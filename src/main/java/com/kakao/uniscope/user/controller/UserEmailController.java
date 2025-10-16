package com.kakao.uniscope.user.controller;

import com.kakao.uniscope.user.dto.CommonResponseDto;
import com.kakao.uniscope.user.dto.SendCodeRequest;
import com.kakao.uniscope.user.dto.VerifyCodeRequest;
import com.kakao.uniscope.user.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "유저 관련 API")
@RestController
@RequestMapping("/api/users/email")
@RequiredArgsConstructor
@Validated
public class UserEmailController {

    private final EmailService emailService;

    @Operation(summary = "인증 코드 전송", description = "입력된 이메일 주소로 인증 코드를 전송합니다.")
    @PostMapping("/send-code")
    public ResponseEntity<Void> sendCode(@RequestBody SendCodeRequest request) {
        emailService.sendVerificationCode(request.email());
        return ResponseEntity.noContent().build(); // 204
    }

    @Operation(summary = "인증 코드 확인", description = "이메일로 전송된 인증 코드가 일치하는지 확인합니다.")
    @PostMapping("/verify-code")
    public ResponseEntity<Void> verifyCode(@RequestBody VerifyCodeRequest request) {
        boolean verified = emailService.verifyCode(request.email(), request.code());
        if (verified) {
            return ResponseEntity.noContent().build(); // 204 
        }
        return ResponseEntity.badRequest().build(); // 400 
    }
}


