package com.kakao.uniscope.professor.repository;

import com.kakao.uniscope.professor.entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {

}
