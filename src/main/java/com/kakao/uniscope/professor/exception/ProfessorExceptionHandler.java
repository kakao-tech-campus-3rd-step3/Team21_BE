package com.kakao.uniscope.professor.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProfessorExceptionHandler {

    @ExceptionHandler(ProfessorNotFoundException.class)
    public ResponseEntity<Void> handleProfessorNotFound(ProfessorNotFoundException e) {
        return ResponseEntity.notFound().build();
    }
}
