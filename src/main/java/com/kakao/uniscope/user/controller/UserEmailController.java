package com.kakao.uniscope.user.controller;

import com.kakao.uniscope.user.dto.CommonResponseDto;
import com.kakao.uniscope.user.dto.SendCodeRequest;
import com.kakao.uniscope.user.dto.VerifyCodeRequest;
import com.kakao.uniscope.user.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users/email")
@RequiredArgsConstructor
@Validated
public class UserEmailController {

    private final EmailService emailService;

    @PostMapping("/send-code")
    public ResponseEntity<Void> sendCode(@RequestBody SendCodeRequest request) {
        emailService.sendVerificationCode(request.email());
        return ResponseEntity.noContent().build(); // 204
    }

    @PostMapping("/verify-code")
    public ResponseEntity<Void> verifyCode(@RequestBody VerifyCodeRequest request) {
        boolean verified = emailService.verifyCode(request.email(), request.code());
        if (verified) {
            return ResponseEntity.noContent().build(); // 204 
        }
        return ResponseEntity.badRequest().build(); // 400 
    }
}


