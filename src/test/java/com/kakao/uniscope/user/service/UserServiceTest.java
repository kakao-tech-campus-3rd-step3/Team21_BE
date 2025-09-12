package com.kakao.uniscope.user.service;

import com.kakao.uniscope.user.dto.UserLoginRequestDto;
import com.kakao.uniscope.user.dto.UserSignupRequestDto;
import com.kakao.uniscope.user.entity.User;
import com.kakao.uniscope.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입 성공")
    void signup_Success() {
        // given
        UserSignupRequestDto requestDto = UserSignupRequestDto.builder()
                .userEmail("test@example.com")
                .userId("testuser")
                .userPwd("password123")
                .build();

        // when
        userService.signup(requestDto);

        // then
        User savedUser = userRepository.findByUserId("testuser").orElse(null);
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getUserEmail()).isEqualTo("test@example.com");
        assertThat(savedUser.getUserId()).isEqualTo("testuser");
        assertThat(passwordEncoder.matches("password123", savedUser.getUserPwd())).isTrue();
    }

    @Test
    @DisplayName("회원가입 실패 - 이메일 중복")
    void signup_Fail_DuplicateEmail() {
        // given
        User existingUser = User.builder()
                .userEmail("test@example.com")
                .userId("existinguser")
                .userPwd("password123")
                .build();
        userRepository.save(existingUser);

        UserSignupRequestDto requestDto = UserSignupRequestDto.builder()
                .userEmail("test@example.com")
                .userId("newuser")
                .userPwd("password123")
                .build();

        // when & then
        assertThatThrownBy(() -> userService.signup(requestDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 사용 중인 이메일입니다.");
    }

    @Test
    @DisplayName("회원가입 실패 - 아이디 중복")
    void signup_Fail_DuplicateUserId() {
        // given
        User existingUser = User.builder()
                .userEmail("existing@example.com")
                .userId("testuser")
                .userPwd("password123")
                .build();
        userRepository.save(existingUser);

        UserSignupRequestDto requestDto = UserSignupRequestDto.builder()
                .userEmail("new@example.com")
                .userId("testuser")
                .userPwd("password123")
                .build();

        // when & then
        assertThatThrownBy(() -> userService.signup(requestDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 사용 중인 아이디입니다.");
    }

    @Test
    @DisplayName("로그인 성공")
    void login_Success() {
        // given
        User user = User.builder()
                .userEmail("test@example.com")
                .userId("testuser")
                .userPwd(passwordEncoder.encode("password123"))
                .build();
        userRepository.save(user);

        UserLoginRequestDto requestDto = UserLoginRequestDto.builder()
                .userId("testuser")
                .userPwd("password123")
                .build();

        // when
        var result = userService.login(requestDto);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo("testuser");
        assertThat(result.getAccessToken()).isNotEmpty();
    }

    @Test
    @DisplayName("로그인 실패 - 존재하지 않는 사용자")
    void login_Fail_UserNotFound() {
        // given
        UserLoginRequestDto requestDto = UserLoginRequestDto.builder()
                .userId("nonexistent")
                .userPwd("password123")
                .build();

        // when & then
        assertThatThrownBy(() -> userService.login(requestDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 사용자입니다.");
    }

    @Test
    @DisplayName("로그인 실패 - 잘못된 비밀번호")
    void login_Fail_WrongPassword() {
        // given
        User user = User.builder()
                .userEmail("test@example.com")
                .userId("testuser")
                .userPwd(passwordEncoder.encode("correctpassword"))
                .build();
        userRepository.save(user);

        UserLoginRequestDto requestDto = UserLoginRequestDto.builder()
                .userId("testuser")
                .userPwd("wrongpassword")
                .build();

        // when & then
        assertThatThrownBy(() -> userService.login(requestDto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("비밀번호가 일치하지 않습니다.");
    }
}