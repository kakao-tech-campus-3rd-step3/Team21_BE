package com.kakao.uniscope.univ;

import com.kakao.uniscope.college.entity.College;
import com.kakao.uniscope.univ.entity.University;
import com.kakao.uniscope.univ.review.entity.UnivReview;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class TestObjectFactory {
    public static University createUniv1() {
        College engineeringCollege = College.builder()
                .collegeSeq(1L)
                .collegeName("공과대학")
                .collegeStudentNum(5000)
                .collegeEstablishedYear("1980")
                .collegeTel("042-111-1111")
                .collegeHomePage("http://eng.cnu.ac.kr")
                .build();

        College naturalScienceCollege = College.builder()
                .collegeSeq(2L)
                .collegeName("자연과학대학")
                .collegeStudentNum(3000)
                .collegeEstablishedYear("1975")
                .collegeTel("042-222-2222")
                .collegeHomePage("http://sci.cnu.ac.kr")
                .build();

        College humanitiesCollege = College.builder()
                .collegeSeq(3L)
                .collegeName("인문대학")
                .collegeStudentNum(2000)
                .collegeEstablishedYear("1960")
                .collegeTel("042-333-3333")
                .collegeHomePage("http://human.cnu.ac.kr")
                .build();

        Set<College> colleges = Set.of(engineeringCollege, naturalScienceCollege, humanitiesCollege);

        UnivReview reviewA = UnivReview.builder()
                .univReviewSeq(100L)
                .overallScore(5)
                .reviewText("캠퍼스가 아름답고 시설이 최신입니다.")
                .createDate(LocalDateTime.now())
                .build();

        UnivReview reviewB = UnivReview.builder()
                .univReviewSeq(101L)
                .overallScore(4)
                .reviewText("학식이 맛있고 기숙사 관리가 잘 됩니다.")
                .createDate(LocalDateTime.now().minusDays(5))
                .build();

        Set<UnivReview> reviews = Set.of(reviewA, reviewB);

        return University.builder()
                .univSeq(1L)
                .name("충남대학교")
                .address("대전광역시 유성구 대학로 99")
                .tel("042-821-1111")
                .homePage("http://www.cnu.ac.kr")
                .imageUrl("http://www.cnu.ac.kr/img/logo.png")
                .establishedYear("1952")
                .totalStudent(28500)
                .campusCnt(3)
                .colleges(colleges)
                .reviews(reviews)
                .build();
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
                .build();
    }
}
