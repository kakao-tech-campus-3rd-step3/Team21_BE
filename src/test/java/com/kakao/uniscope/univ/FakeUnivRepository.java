package com.kakao.uniscope.univ;

import com.kakao.uniscope.univ.entity.University;
import com.kakao.uniscope.univ.repository.UnivRepository;
import java.util.*;

public class FakeUnivRepository implements UnivRepository {

    private final Map<Long, University> database = new HashMap<>();

    public FakeUnivRepository() {
        // 더미 데이터 초기화
        University univ1 = TestObjectFactory.createUniv1();
        University univ2 = TestObjectFactory.createUniv2();

        save(univ1);
        save(univ2);
    }

    public void save(University university) {
        database.put(university.getUnivSeq(), university);
    }

    @Override
    public Optional<University> findWithFullDetailsByUnivSeq(Long univSeq) {
        return Optional.ofNullable(database.get(univSeq));
    }

    @Override
    public Optional<University> findWithCollegesByUnivSeq(Long univSeq) {
        return Optional.ofNullable(database.get(univSeq));
    }
}
