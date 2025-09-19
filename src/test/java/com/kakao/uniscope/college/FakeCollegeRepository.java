package com.kakao.uniscope.college;

import com.kakao.uniscope.college.entity.College;
import com.kakao.uniscope.college.repository.CollegeRepository;
import com.kakao.uniscope.department.entity.Department;

import java.util.*;

public class FakeCollegeRepository implements CollegeRepository {

    private final Map<Long, College> database = new HashMap<>();

    public FakeCollegeRepository() {
        // 더미 데이터 초기화
        Department dept1 = Department.builder()
                .deptSeq(1L)
                .deptName("컴퓨터공학과")
                .homePage("http://cs.ac.kr")
                .build();

        Department dept2 = Department.builder()
                .deptSeq(2L)
                .deptName("정보통신공학과")
                .homePage("http://ice.ac.kr")
                .build();

        College college1 = College.builder()
                .collegeSeq(1L)
                .collegeName("공과대학")
                .departments(List.of(dept1, dept2))
                .build();

        save(college1);
    }

    public void save(College college) {
        database.put(college.getCollegeSeq(), college);
    }

    public Optional<College> findById(Long collegeSeq) {
        return Optional.ofNullable(database.get(collegeSeq));
    }

    public List<College> findByUniversitySeq(Long univSeq) {
        return new ArrayList<>(database.values());
    }
}
