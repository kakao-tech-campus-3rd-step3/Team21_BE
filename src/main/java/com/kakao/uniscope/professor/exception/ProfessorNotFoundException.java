package com.kakao.uniscope.professor.exception;

public class ProfessorNotFoundException extends RuntimeException{
    public ProfessorNotFoundException(Long profSeq) {
        super("교수를 찾을 수 없습니다. 교수 번호: " + profSeq);
    }
}
