package com.kakao.uniscope.univ;

import com.kakao.uniscope.univ.entity.University;
import com.kakao.uniscope.univ.repository.UnivRepository;
import java.util.*;
import java.util.stream.Collectors;

public class FakeUnivRepository implements UnivRepository {

    private final Map<Long, University> database = new HashMap<>();

    public FakeUnivRepository() {
        // 더미 데이터 초기화
        University univ1 = TestObjectFactory.createUniv1();
        University univ2 = TestObjectFactory.createUniv2();
        University univ3 = TestObjectFactory.createUniv3();

        save(univ1);
        save(univ2);
        save(univ3);
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

    @Override
    public List<University> findWithReviewsByUnivSeqIn(List<Long> univSeqs) {
        return database.values().stream()
                .filter(univ -> univSeqs.contains(univ.getUnivSeq()))
                .collect(Collectors.toList());
    }

    @Override
    public List<University> findAll() {
        return new ArrayList<>(database.values());
    }
}
