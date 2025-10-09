package com.kakao.uniscope.univ;

import com.kakao.uniscope.college.entity.College;
import com.kakao.uniscope.univ.entity.University;
import com.kakao.uniscope.univ.review.entity.UnivReview;

import java.time.LocalDateTime;
import java.util.HashSet;

public class TestObjectFactory {

    public static University createUniv1() {
        University university = University.builder()
                .univSeq(1L)
                .name("충남대학교")
                .address("대전광역시 유성구 대학로 99")
                .tel("042-821-1111")
                .homePage("http://www.cnu.ac.kr")
                .imageUrl("http://www.cnu.ac.kr/img/logo.png")
                .establishedYear("1952")
                .totalStudent(28500)
                .campusCnt(3)
                .build();

        College engineeringCollege = College.builder()
                .collegeSeq(1L)
                .collegeName("공과대학")
                .collegeStudentNum(5000)
                .collegeEstablishedYear("1980")
                .collegeTel("042-111-1111")
                .collegeHomePage("http://eng.cnu.ac.kr")
                .university(university)
                .build();

        College naturalScienceCollege = College.builder()
                .collegeSeq(2L)
                .collegeName("자연과학대학")
                .collegeStudentNum(3000)
                .collegeEstablishedYear("1975")
                .collegeTel("042-222-2222")
                .collegeHomePage("http://sci.cnu.ac.kr")
                .university(university)
                .build();

        UnivReview reviewA = UnivReview.builder()
                .univReviewSeq(1L)
                .university(university)
                .foodScore(4)
                .dormScore(5)
                .convScore(3)
                .campusScore(5)
                .welfareScore(4)
                .reviewText("캠퍼스가 아름답고 시설이 최신입니다.")
                .createDate(LocalDateTime.now())
                .build();

        UnivReview reviewB = UnivReview.builder()
                .univReviewSeq(101L)
                .university(university)
                .foodScore(5)
                .dormScore(4)
                .convScore(4)
                .campusScore(4)
                .welfareScore(5)
                .reviewText("학식이 맛있고 기숙사 관리가 잘 됩니다.")
                .createDate(LocalDateTime.now().minusDays(5))
                .build();

        university.getColleges().add(engineeringCollege);
        university.getColleges().add(naturalScienceCollege);

        university.getReviews().add(reviewA);
        university.getReviews().add(reviewB);

        return university;
    }

    public static University createUniv2() {
        return University.builder()
                .univSeq(2L)
                .name("서울대학교")
                .address("서울특별시 관악구 관악로 1")
                .tel("02-880-5114")
                .homePage("http://www.snu.ac.kr")
                .imageUrl("http://www.snu.ac.kr/img/logo.png")
                .establishedYear("1946")
                .totalStudent(30000)
                .campusCnt(4)
                .colleges(new HashSet<>())
                .reviews(new HashSet<>())
                .build();
    }
}
