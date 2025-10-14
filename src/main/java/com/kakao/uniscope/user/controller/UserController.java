package com.kakao.uniscope.user.controller;

import com.kakao.uniscope.user.dto.CommonResponseDto;
import com.kakao.uniscope.user.dto.UserLoginRequestDto;
import com.kakao.uniscope.user.dto.UserLoginResponseDto;
import com.kakao.uniscope.user.dto.UserSignupRequestDto;
import com.kakao.uniscope.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "유저 관련 API", description = "유저 관련 API 입니다.")
@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "회원가입", description = "새로운 사용자 계정을 생성합니다.")
    @PostMapping("/signup")
    public ResponseEntity<CommonResponseDto<Void>> signup(@Valid @RequestBody UserSignupRequestDto requestDto) {
        userService.signup(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponseDto.ok("회원가입이 완료되었습니다."));
    }

    @Operation(summary = "로그인 및 JWT 발급", description = "아이디와 비밀번호를 검증하고 JWT 토큰을 발급합니다.")
    @PostMapping("/login")
    public ResponseEntity<CommonResponseDto<UserLoginResponseDto>> login(@Valid @RequestBody UserLoginRequestDto requestDto) {
        UserLoginResponseDto loginResponse = userService.login(requestDto);
        return ResponseEntity.ok(CommonResponseDto.ok("로그인에 성공했습니다.", loginResponse));
    }

}
