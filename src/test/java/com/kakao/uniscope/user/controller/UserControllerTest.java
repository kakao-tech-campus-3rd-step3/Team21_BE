package com.kakao.uniscope.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakao.uniscope.user.dto.UserLoginRequestDto;
import com.kakao.uniscope.user.dto.UserSignupRequestDto;
import com.kakao.uniscope.user.entity.User;
import com.kakao.uniscope.user.repository.UserRepository;
import com.kakao.uniscope.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureWebMvc
@ActiveProfiles("test")
@Transactional
class UserControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입 성공 테스트")
    void signup_Success() throws Exception {
        // given
        UserSignupRequestDto requestDto = UserSignupRequestDto.builder()
                .userEmail("test@example.com")
                .userId("testuser")
                .userPwd("password123")
                .build();

        // when & then
        mockMvc.perform(post("/api/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("회원가입이 완료되었습니다."))
                .andExpect(jsonPath("$.data").doesNotExist());
    }

    @Test
    @DisplayName("회원가입 실패 - 이메일 중복")
    void signup_Fail_DuplicateEmail() throws Exception {
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
        mockMvc.perform(post("/api/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("이미 사용 중인 이메일입니다."));
    }

    @Test
    @DisplayName("회원가입 실패 - 아이디 중복")
    void signup_Fail_DuplicateUserId() throws Exception {
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
        mockMvc.perform(post("/api/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("이미 사용 중인 아이디입니다."));
    }

    @Test
    @DisplayName("회원가입 실패 - 유효성 검증 실패")
    void signup_Fail_ValidationError() throws Exception {
        // given
        UserSignupRequestDto requestDto = UserSignupRequestDto.builder()
                .userEmail("invalid-email")
                .userId("")
                .userPwd("123")
                .build();

        // when & then
        mockMvc.perform(post("/api/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("로그인 성공 테스트")
    void login_Success() throws Exception {
        // given
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = User.builder()
                .userEmail("test@example.com")
                .userId("testuser")
                .userPwd(encoder.encode("password123"))
                .build();
        userRepository.save(user);

        UserLoginRequestDto requestDto = UserLoginRequestDto.builder()
                .userId("testuser")
                .userPwd("password123")
                .build();

        // when & then
        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("로그인에 성공했습니다."))
                .andExpect(jsonPath("$.data.accessToken").exists())
                .andExpect(jsonPath("$.data.userId").value("testuser"));
    }

    @Test
    @DisplayName("로그인 실패 - 존재하지 않는 사용자")
    void login_Fail_UserNotFound() throws Exception {
        // given
        UserLoginRequestDto requestDto = UserLoginRequestDto.builder()
                .userId("nonexistent")
                .userPwd("password123")
                .build();

        // when & then
        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("존재하지 않는 사용자입니다."));
    }

    @Test
    @DisplayName("로그인 실패 - 잘못된 비밀번호")
    void login_Fail_WrongPassword() throws Exception {
        // given
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = User.builder()
                .userEmail("test@example.com")
                .userId("testuser")
                .userPwd(encoder.encode("correctpassword"))
                .build();
        userRepository.save(user);

        UserLoginRequestDto requestDto = UserLoginRequestDto.builder()
                .userId("testuser")
                .userPwd("wrongpassword")
                .build();

        // when & then
        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("비밀번호가 일치하지 않습니다."));
    }

    @Test
    @DisplayName("로그인 실패 - 유효성 검증 실패")
    void login_Fail_ValidationError() throws Exception {
        // given
        UserLoginRequestDto requestDto = UserLoginRequestDto.builder()
                .userId("")
                .userPwd("")
                .build();

        // when & then
        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}