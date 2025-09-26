package com.kakao.uniscope.univ.univReview;

import com.kakao.uniscope.univ.TestObjectFactory;
import com.kakao.uniscope.univ.entity.University;
import com.kakao.uniscope.univ.review.entity.UnivReview;
import com.kakao.uniscope.univ.review.repository.UnivReviewRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FakeUnivReviewRepository implements UnivReviewRepository {

    private final List<UnivReview> reviews;

    public FakeUnivReviewRepository() {
        University univ1 = TestObjectFactory.createUniv1();
        University univ2 = TestObjectFactory.createUniv2();
        this.reviews = createDummyReviews(univ1, univ2);
    }

    @Override
    public Page<UnivReview> findByUniversity(University university, Pageable pageable) {
        List<UnivReview> filteredReviews = reviews.stream()
                .filter(review -> review.getUniversity().getUnivSeq().equals(university.getUnivSeq()))
                .sorted(Comparator.comparing(UnivReview::getCreateDate).reversed()) // 최신순으로 정렬
                .collect(Collectors.toList());

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), filteredReviews.size());

        if (start > filteredReviews.size()) {
            return new PageImpl<>(Collections.emptyList(), pageable, filteredReviews.size());
        }

        List<UnivReview> pageContent = filteredReviews.subList(start, end);

        return new PageImpl<>(pageContent, pageable, filteredReviews.size());
    }

    private List<UnivReview> createDummyReviews(University univ1, University univ2) {
        return List.of(
                UnivReview.builder().university(univ1).foodScore(5).dormScore(5).convScore(5).campusScore(5).overallScore(5).reviewText("리뷰1").createUser("user1").createDate(LocalDateTime.now()).build(),
                UnivReview.builder().university(univ1).foodScore(4).dormScore(4).convScore(4).campusScore(4).overallScore(4).reviewText("리뷰2").createUser("user2").createDate(LocalDateTime.now().minusDays(1)).build(),
                UnivReview.builder().university(univ1).foodScore(3).dormScore(3).convScore(3).campusScore(3).overallScore(3).reviewText("리뷰3").createUser("user3").createDate(LocalDateTime.now().minusDays(2)).build(),
                UnivReview.builder().university(univ1).foodScore(5).dormScore(5).convScore(5).campusScore(5).overallScore(5).reviewText("리뷰4").createUser("user4").createDate(LocalDateTime.now()).build()
        );
    }
}
