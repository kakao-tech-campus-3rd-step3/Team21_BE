package com.kakao.uniscope.user.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class JwtTokenProviderTest {

    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    void setUp() {
        // 테스트용 시크릿 키 설정
        System.setProperty("JWT_SECRET", "test-secret-key-for-jwt-token-provider-testing-purposes-only");
        System.setProperty("JWT_TOKEN_VALIDITY_IN_SECONDS", "3600");
        
        jwtTokenProvider = new JwtTokenProvider(
                System.getProperty("JWT_SECRET"),
                Long.parseLong(System.getProperty("JWT_TOKEN_VALIDITY_IN_SECONDS"))
        );
    }

    @Test
    @DisplayName("JWT 토큰 생성 성공")
    void generateToken_Success() {
        // given
        String userId = "testuser";
        Long userSeq = 1L;

        // when
        String token = jwtTokenProvider.generateToken(userId, userSeq);

        // then
        assertThat(token).isNotNull();
        assertThat(token).isNotEmpty();
        assertThat(token.split("\\.")).hasSize(3); // JWT는 3개 부분으로 구성
    }

    @Test
    @DisplayName("JWT 토큰 유효성 검증 성공")
    void validateToken_Success() {
        // given
        String userId = "testuser";
        Long userSeq = 1L;
        String token = jwtTokenProvider.generateToken(userId, userSeq);

        // when
        boolean isValid = jwtTokenProvider.validateToken(token);

        // then
        assertThat(isValid).isTrue();
    }

    @Test
    @DisplayName("JWT 토큰에서 사용자 ID 추출 성공")
    void getUserIdFromToken_Success() {
        // given
        String userId = "testuser";
        Long userSeq = 1L;
        String token = jwtTokenProvider.generateToken(userId, userSeq);

        // when
        String extractedUserId = jwtTokenProvider.getUserIdFromToken(token);

        // then
        assertThat(extractedUserId).isEqualTo(userId);
    }

    @Test
    @DisplayName("잘못된 JWT 토큰 유효성 검증 실패")
    void validateToken_Fail_InvalidToken() {
        // given
        String invalidToken = "invalid.jwt.token";

        // when
        boolean isValid = jwtTokenProvider.validateToken(invalidToken);

        // then
        assertThat(isValid).isFalse();
    }

    @Test
    @DisplayName("만료된 JWT 토큰 유효성 검증 실패")
    void validateToken_Fail_ExpiredToken() {
        // given
        // 만료된 토큰을 생성하기 위해 유효시간을 0으로 설정
        JwtTokenProvider expiredTokenProvider = new JwtTokenProvider(
                System.getProperty("JWT_SECRET"),
                0L // 즉시 만료
        );
        String expiredToken = expiredTokenProvider.generateToken("testuser", 1L);

        // when
        boolean isValid = jwtTokenProvider.validateToken(expiredToken);

        // then
        assertThat(isValid).isFalse();
    }

    @Test
    @DisplayName("JWT 토큰 만료 여부 확인")
    void isTokenExpired() {
        // given
        String userId = "testuser";
        Long userSeq = 1L;
        String token = jwtTokenProvider.generateToken(userId, userSeq);

        // when
        boolean isExpired = jwtTokenProvider.isTokenExpired(token);

        // then
        assertThat(isExpired).isFalse();
    }

    @Test
    @DisplayName("JWT 토큰에서 사용자 시퀀스 추출")
    void getUserSeqFromToken() {
        // given
        String userId = "testuser";
        Long userSeq = 1L;
        String token = jwtTokenProvider.generateToken(userId, userSeq);

        // when
        Long extractedUserSeq = jwtTokenProvider.getUserSeqFromToken(token);

        // then
        assertThat(extractedUserSeq).isEqualTo(userSeq);
    }
}