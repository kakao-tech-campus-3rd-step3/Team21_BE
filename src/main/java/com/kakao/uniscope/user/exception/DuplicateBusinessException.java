package com.kakao.uniscope.user.exception;

import org.springframework.http.HttpStatus;

public class DuplicateBusinessException extends BusinessException {
    public DuplicateBusinessException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}

