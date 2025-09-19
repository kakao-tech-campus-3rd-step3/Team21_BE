package com.kakao.uniscope.lecture.repository;

import com.kakao.uniscope.lecture.entity.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureRepository extends JpaRepository<Lecture, Long> {

}
