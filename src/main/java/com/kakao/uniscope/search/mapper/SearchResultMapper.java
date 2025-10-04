package com.kakao.uniscope.search.mapper;

import com.kakao.uniscope.college.entity.College;
import com.kakao.uniscope.department.entity.Department;
import com.kakao.uniscope.lecture.review.repository.LectureReviewRepository;
import com.kakao.uniscope.professor.entity.Professor;
import com.kakao.uniscope.search.dto.DeptSearchResultDto;
import com.kakao.uniscope.search.dto.ProfSearchResultDto;
import com.kakao.uniscope.search.dto.UnivDeptSearchResultDto;
import com.kakao.uniscope.search.dto.UnivProfSearchResultDto;
import com.kakao.uniscope.search.dto.UnivSearchResultDto;
import com.kakao.uniscope.univ.entity.University;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SearchResultMapper {

    private final LectureReviewRepository lectureReviewRepository;

    public UnivSearchResultDto toUnivResult(University univ) {
        return UnivSearchResultDto.from(
                univ,
                univ.getAverageRating(),
                (int) univ.getReviewCount()
        );
    }

    public ProfSearchResultDto toProfResult(Professor prof) {
        return ProfSearchResultDto.from(
                prof,
                lectureReviewRepository.findOverallLectureRating(prof.getProfSeq()),
                lectureReviewRepository.countByProfessorId(prof.getProfSeq())
        );
    }

    public DeptSearchResultDto toDeptResult(Department dept, University univ) {
        return DeptSearchResultDto.from(
                dept,
                univ,
                univ.getAverageRating(),
                (int) univ.getReviewCount()
        );
    }

    public UnivDeptSearchResultDto toUnivDeptResult(
            University univ,
            College college,
            Department dept
    ) {
        return UnivDeptSearchResultDto.from(
                univ,
                college,
                dept,
                univ.getAverageRating(),
                (int) univ.getReviewCount()
        );
    }

    public UnivProfSearchResultDto toUnivProfResult(
            University univ,
            College college,
            Department dept,
            Professor prof
    ) {
        return UnivProfSearchResultDto.from(
                univ,
                college,
                dept,
                prof,
                lectureReviewRepository.findOverallLectureRating(prof.getProfSeq()),
                lectureReviewRepository.countByProfessorId(prof.getProfSeq())
        );
    }
}
