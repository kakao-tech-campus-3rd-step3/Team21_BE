package com.kakao.uniscope.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "USERS")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_SEQ")
    private Long userSeq;

    @Column(name = "USER_EMAIL", length = 1000, nullable = false, unique = true)
    private String userEmail;

    @Column(name = "USER_ID", length = 100, nullable = false, unique = true)
    private String userId;

    @Column(name = "USER_PWD", length = 1000, nullable = false)
    private String userPwd;

    @Column(name = "CREATE_USER", length = 100)
    private String createUser;

    @Column(name = "MODIFY_USER", length = 100)
    private String modifyUser;

    @CreatedDate
    @Column(name = "CREATE_DATE")
    private LocalDateTime createDate;

    @LastModifiedDate
    @Column(name = "MODIFY_DATE")
    private LocalDateTime modifyDate;

    @Column(name = "DELT_YN", length = 1)
    private String deltYn = "N";

    @Builder
    public User(String userEmail, String userId, String userPwd, String createUser) {
        this.userEmail = userEmail;
        this.userId = userId;
        this.userPwd = userPwd;
        this.createUser = createUser;
        this.deltYn = "N";
    }

    public void updatePassword(String newPassword) {
        this.userPwd = newPassword;
    }

    public void softDelete() {
        this.deltYn = "Y";
    }
}
