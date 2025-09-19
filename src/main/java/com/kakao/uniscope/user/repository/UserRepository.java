package com.kakao.uniscope.user.repository;

import com.kakao.uniscope.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    //사용자 ID로 사용자 조회
    Optional<User> findByUserId(String userId);

    // 이메일 중복 체크
    boolean existsByUserEmail(String userEmail);

    //사용자 ID 중복 체크
    boolean existsByUserId(String userId);

    // 삭제되지 않은 사용자 ID로 조회
    Optional<User> findByUserIdAndDeltYn(String userId, String deltYn);

    // 삭제되지 않은 사용자 이메일로 조회
    Optional<User> findByUserEmailAndDeltYn(String userEmail, String deltYn);
}