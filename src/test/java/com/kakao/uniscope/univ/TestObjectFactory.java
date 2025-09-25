package com.kakao.uniscope.univ;

import com.kakao.uniscope.univ.entity.University;

public class TestObjectFactory {
    public static University createUniv1() {
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
