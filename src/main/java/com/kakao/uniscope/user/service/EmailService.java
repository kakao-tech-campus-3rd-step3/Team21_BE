package com.kakao.uniscope.user.service;

import java.time.Instant;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.auth-code-expiration-millis:300000}")
    private long ttlMillis;

    private final Map<String, CodeEntry> emailToCodeStore = new ConcurrentHashMap<>();
    private final Set<String> verifiedEmails = ConcurrentHashMap.newKeySet(); // 검증된 이메일 저장용

    public void sendVerificationCode(String toEmail) {
        String code = generateSixDigitCode();
        emailToCodeStore.put(toEmail, new CodeEntry(code, Instant.now().plusMillis(ttlMillis)));

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("[UniScope] 이메일 인증 코드");
        message.setText("인증코드: " + code + "\n유효시간: " + (ttlMillis / 60000) + "분");

        mailSender.send(message);
        log.info("인증코드 전송 완료: email={}", toEmail);
    }

    // 인증 코드 검증
    public boolean verifyCode(String email, String code) {
        if (!StringUtils.hasText(code)) {
            return false;
        }
        
        CodeEntry entry = emailToCodeStore.get(email); // 이메일에 해당하는 코드 조회
        if (entry == null) {
            return false;
        }
        
        if (Instant.now().isAfter(entry.expiresAt())) { // 만료된 경우
            emailToCodeStore.remove(email);
            return false;
        }
        
        boolean match = Objects.equals(entry.code(), code);
        if (match) {
            emailToCodeStore.remove(email);
            verifiedEmails.add(email); // 검증된 이메일로 추가
        }
        
        return match;
    }

    private String generateSixDigitCode() {
        return String.format("%06d", ThreadLocalRandom.current().nextInt(0, 1_000_000));
    }

    // 인증 코드와 만료 시간 저장용 레코드
    private record CodeEntry(String code, Instant expiresAt) {}

    // 이메일이 검증되었는지 확인
    public boolean isEmailVerified(String email) {
        return verifiedEmails.contains(email);
    }
}
