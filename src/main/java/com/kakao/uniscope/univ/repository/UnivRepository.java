package com.kakao.uniscope.univ.repository;

import com.kakao.uniscope.univ.entity.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnivRepository extends JpaRepository<University, Long> { }
