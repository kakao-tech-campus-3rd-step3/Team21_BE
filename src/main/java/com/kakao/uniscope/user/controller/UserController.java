package com.kakao.uniscope.user.controller;

import com.kakao.uniscope.user.dto.CommonResponseDto;
import com.kakao.uniscope.user.dto.UserLoginRequestDto;
import com.kakao.uniscope.user.dto.UserLoginResponseDto;
import com.kakao.uniscope.user.dto.UserSignupRequestDto;
import com.kakao.uniscope.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //회원가입
    @PostMapping("/signup")
    public ResponseEntity<CommonResponseDto<Void>> signup(@Valid @RequestBody UserSignupRequestDto requestDto) {
        userService.signup(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponseDto.ok("회원가입이 완료되었습니다."));
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<CommonResponseDto<UserLoginResponseDto>> login(@Valid @RequestBody UserLoginRequestDto requestDto) {
        UserLoginResponseDto loginResponse = userService.login(requestDto);
        return ResponseEntity.ok(CommonResponseDto.ok("로그인에 성공했습니다.", loginResponse));
    }

    // 아이디 중복 확인: 중복이면 409, 사용 가능이면 204
    @GetMapping("/check-id")
    public ResponseEntity<Void> checkUserId(@RequestParam String userId) {
        boolean duplicated = userService.existsByUserId(userId);
        return duplicated ? ResponseEntity.status(409).build() : ResponseEntity.noContent().build();
    }
}
