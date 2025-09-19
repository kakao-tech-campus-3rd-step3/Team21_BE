package com.kakao.uniscope.univ;

import com.kakao.uniscope.college.entity.College;
import com.kakao.uniscope.univ.entity.University;
import com.kakao.uniscope.univ.repository.UnivRepository;
import com.kakao.uniscope.univ.review.entity.UnivReview;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FakeUnivRepository implements UnivRepository {

    private final Map<Long, University> database = new HashMap<>();

    public FakeUnivRepository() {
        // 더미 데이터 초기화
        University univ1 = University.builder()
                .univSeq(1L)
                .name("충남대학교")
                .colleges(List.of(
                        College.builder().collegeSeq(1L).collegeName("공과대학").build(),
                        College.builder().collegeSeq(2L).collegeName("자연과학대학").build()
                ))
                .reviews(List.of(
                        UnivReview.builder().univReviewSeq(10L).overallScore(4).reviewText("리뷰1").createDate(LocalDateTime.now().minusDays(1)).build(),
                        UnivReview.builder().univReviewSeq(11L).overallScore(5).reviewText("리뷰2").createDate(LocalDateTime.now()).build(),
                        UnivReview.builder().univReviewSeq(12L).overallScore(3).reviewText("리뷰3").createDate(LocalDateTime.now().minusDays(2)).build()
                ))
                .build();

        save(univ1);

        University univ2 = University.builder()
                .univSeq(2L)
                .name("서울대학교")
                .colleges(List.of(
                        College.builder().collegeSeq(3L).collegeName("인문대학").build()
                ))
                .reviews(List.of())
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
