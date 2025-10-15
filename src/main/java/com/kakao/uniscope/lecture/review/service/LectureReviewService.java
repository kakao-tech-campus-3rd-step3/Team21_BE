package com.kakao.uniscope.lecture.review.service;

import com.kakao.uniscope.common.exception.ResourceNotFoundException;
import com.kakao.uniscope.lecture.entity.Lecture;
import com.kakao.uniscope.lecture.repository.LectureRepository;
import com.kakao.uniscope.lecture.review.dto.LectureReviewRequest;
import com.kakao.uniscope.lecture.review.dto.LectureReviewResponse;
import com.kakao.uniscope.lecture.review.entity.LectureReview;
import com.kakao.uniscope.lecture.review.repository.LectureReviewRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LectureReviewService {

    private final LectureReviewRepository lectureReviewRepository;
    private final LectureRepository lectureRepository;

    @Transactional
    public LectureReviewResponse createReview(LectureReviewRequest request) {
        Lecture lecture = lectureRepository.findById(request.lecSeq())
                .orElseThrow(() -> new ResourceNotFoundException(
                        request.lecSeq() + "에 해당하는 강의를 찾을 수 없습니다."));

        LectureReview newReview = LectureReview.builder()
                .lecture(lecture)
                .semester(request.getSemesterString())
                .lecDifficulty(request.lecDifficulty())
                .gradeDistribution(request.gradeDistribution())
                .examDifficulty(request.examDifficulty())
                .homework(request.homework())
                .groupProjReq(request.groupProjReq())
                .overallReview(request.overallReview())
                .createdDate(LocalDateTime.now())
                .build();

        LectureReview savedReview = lectureReviewRepository.save(newReview);

        return LectureReviewResponse.of(savedReview.getLecReviewSeq());
    }
}
