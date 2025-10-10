package com.kakao.uniscope.user.service;

import com.kakao.uniscope.user.dto.UserLoginRequestDto;
import com.kakao.uniscope.user.dto.UserLoginResponseDto;
import com.kakao.uniscope.user.dto.UserSignupRequestDto;
import com.kakao.uniscope.user.entity.User;
import com.kakao.uniscope.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kakao.uniscope.user.service.JwtTokenProvider;
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final EmailService emailService;

    //회원가입
    @Transactional
    public void signup(UserSignupRequestDto requestDto) {
        // 이메일 중복 체크
        if (userRepository.existsByUserEmail(requestDto.getUserEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        // 아이디 중복 체크
        if (userRepository.existsByUserId(requestDto.getUserId())) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }

        // 이메일 인증 여부 확인
        if (!emailService.isEmailVerified(requestDto.getUserEmail())) {
            throw new IllegalArgumentException("이메일 인증이 완료되지 않았습니다.");
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(requestDto.getUserPwd());

        // 사용자 생성
        User user = User.builder()
                .userEmail(requestDto.getUserEmail())
                .userId(requestDto.getUserId())
                .userPwd(encodedPassword)
                .createUser(requestDto.getUserId())
                .build();

        userRepository.save(user);
        log.info("회원가입 완료: userId={}", requestDto.getUserId());
    }

    //로그인
    public UserLoginResponseDto login(UserLoginRequestDto requestDto) {
        // 사용자 조회
        User user = userRepository.findByUserIdAndDeltYn(requestDto.getUserId(), "N")
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        // 비밀번호 확인
        if (!passwordEncoder.matches(requestDto.getUserPwd(), user.getUserPwd())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // JWT 토큰 생성
        String accessToken = jwtTokenProvider.generateToken(user.getUserId(), user.getUserSeq());

        log.info("로그인 성공: userId={}", user.getUserId());

        return UserLoginResponseDto.builder()
                .accessToken(accessToken)
                .userSeq(user.getUserSeq())
                .userId(user.getUserId())
                .build();
    }

    //사용자 ID로 사용자 조회
    public User findByUserId(String userId) {
        return userRepository.findByUserIdAndDeltYn(userId, "N")
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
    }

    //사용자 시퀀스로 사용자 조회
    public User findByUserSeq(Long userSeq) {
        return userRepository.findById(userSeq)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
    }

    //이메일 중복 체크
    public boolean existsByEmail(String email) {
        return userRepository.existsByUserEmail(email);
    }

    //아이디 중복 체크
    public boolean existsByUserId(String userId) {
        return userRepository.existsByUserId(userId);
    }

    //비밀번호 변경
    @Transactional
    public void updatePassword(String userId, String currentPassword, String newPassword) {
        // 사용자 조회
        User user = findByUserId(userId);
        
        // 현재 비밀번호 확인
        if (!passwordEncoder.matches(currentPassword, user.getUserPwd())) {
            throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
        }
        
        String encodedNewPassword = passwordEncoder.encode(newPassword);
        
        user.updatePasswordWithEncoded(encodedNewPassword);
        
        log.info("비밀번호 변경 완료: userId={}", userId);
    }
}
