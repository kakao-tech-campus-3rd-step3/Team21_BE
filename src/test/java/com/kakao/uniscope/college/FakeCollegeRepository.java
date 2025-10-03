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
                .deptAddress("컴공과 주소")
                .deptTel("010-1234-5678")
                .deptEmail("email")
                .deptEstablishedYear("2020")
                .deptIntro("학과 한 줄 평")
                .build();

        Department dept2 = Department.builder()
                .deptSeq(2L)
                .deptName("정보통신공학과")
                .homePage("http://ice.ac.kr")
                .deptAddress("정보통신공학과 주소")
                .deptTel("010-1234-5678")
                .deptEmail("email")
                .deptEstablishedYear("2020")
                .deptIntro("학과 한 줄 평")
                .build();

        College college1 = College.builder()
                .collegeSeq(1L)
                .collegeName("공과대학")
                .collegeStudentNum(5000)
                .collegeEstablishedYear("1980")
                .collegeTel("042-111-1111")
                .collegeHomePage("http://eng.cnu.ac.kr")
                .collegeIntro("한 줄 평")
                .departments(List.of(dept1, dept2))
                .build();

        College college2 = College.builder()
                .collegeSeq(2L)
                .collegeName("자연과학대학")
                .collegeStudentNum(3000)
                .collegeEstablishedYear("1975")
                .collegeTel("042-222-2222")
                .collegeHomePage("http://sci.cnu.ac.kr")
                .collegeIntro("한 줄 평")
                .departments(List.of())
                .build();

        College college3 = College.builder()
                .collegeSeq(3L)
                .collegeName("인문대학")
                .collegeStudentNum(2000)
                .collegeEstablishedYear("1960")
                .collegeTel("042-333-3333")
                .collegeHomePage("http://human.cnu.ac.kr")
                .collegeIntro("한 줄 평")
                .departments(List.of())
                .build();

        save(college1);
        save(college2);
        save(college3);
    }

    public void save(College college) {
        database.put(college.getCollegeSeq(), college);
    }

    public Optional<College> findWithDepartmentsByCollegeSeq(Long collegeSeq) {
        return Optional.ofNullable(database.get(collegeSeq));
    }

    @Override
    public Optional<College> findWithUniversityByCollegeSeq(Long collegeSeq) {
        return Optional.ofNullable(database.get(collegeSeq));
    }
}
