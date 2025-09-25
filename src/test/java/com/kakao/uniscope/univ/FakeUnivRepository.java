package com.kakao.uniscope.univ;

import com.kakao.uniscope.univ.entity.University;
import com.kakao.uniscope.univ.repository.UnivRepository;
import java.util.*;

public class FakeUnivRepository implements UnivRepository {

    private final Map<Long, University> database = new HashMap<>();

    public FakeUnivRepository() {
        // 더미 데이터 초기화
        University univ1 = University.builder()
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

        save(univ1);

        University univ2 = University.builder()
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

        save(univ2);
    }

    public void save(University university) {
        database.put(university.getUnivSeq(), university);
    }

    @Override
    public Optional<University> findById(Long univSeq) {
        return Optional.ofNullable(database.get(univSeq));
    }
}
