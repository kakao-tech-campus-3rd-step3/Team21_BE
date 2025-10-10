package com.kakao.uniscope.comparison.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.charset.StandardCharsets;

@RestControllerAdvice
public class ComparisonExceptionHandler {

    @ExceptionHandler(ComparisonException.class)
    public ResponseEntity<String> handleComparisonException(ComparisonException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(new MediaType("text", "plain", StandardCharsets.UTF_8))
                .body(e.getMessage());
    }
}
