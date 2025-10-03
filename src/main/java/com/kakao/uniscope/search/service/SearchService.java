package com.kakao.uniscope.search.service;

import com.kakao.uniscope.college.entity.College;
import com.kakao.uniscope.department.entity.Department;
import com.kakao.uniscope.lecture.review.repository.LectureReviewRepository;
import com.kakao.uniscope.professor.entity.Professor;
import com.kakao.uniscope.professor.repository.ProfessorRepository;
import com.kakao.uniscope.search.dto.DeptSearchResponseDto;
import com.kakao.uniscope.search.dto.DeptSearchResultDto;
import com.kakao.uniscope.search.dto.ProfSearchResponseDto;
import com.kakao.uniscope.search.dto.ProfSearchResultDto;
import com.kakao.uniscope.search.dto.UnivDeptSearchResponseDto;
import com.kakao.uniscope.search.dto.UnivDeptSearchResultDto;
import com.kakao.uniscope.search.dto.UnivProfSearchResponseDto;
import com.kakao.uniscope.search.dto.UnivProfSearchResultDto;
import com.kakao.uniscope.search.dto.UnivSearchResponseDto;
import com.kakao.uniscope.search.dto.UnivSearchResultDto;
import com.kakao.uniscope.univ.entity.University;
import com.kakao.uniscope.univ.repository.UnivRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final UnivRepository univRepository;
    private final ProfessorRepository professorRepository;

    private final LectureReviewRepository lectureReviewRepository;


    // 1. 대학 검색
    public UnivSearchResponseDto searchUniversity(String keyword, Pageable pageable) {
        List<University> allUnivs = univRepository.findAll();

        List<UnivSearchResultDto> results = allUnivs.stream()
                .filter(univ -> matchesSequentialChars(univ.getName(), keyword))
                .sorted((u1, u2) -> compareRelevance(u1.getName(), u2.getName(), keyword))
                .skip(pageable.getOffset())
                .limit(pageable.getPageSize())
                .map(this::toUnivResult)
                .toList();

        return new UnivSearchResponseDto(results, results.size());
    }

    // 2. 교수 검색
    public ProfSearchResponseDto searchProfessor(String keyword, Pageable pageable) {
        List<Professor> allProfs = professorRepository.findAll();

        List<ProfSearchResultDto> results = allProfs.stream()
                .filter(prof -> matchesSequentialChars(prof.getProfName(), keyword))
                .sorted((p1, p2) -> compareRelevance(p1.getProfName(), p2.getProfName(), keyword))
                .skip(pageable.getOffset())
                .limit(pageable.getPageSize())
                .map(this::toProfResult)
                .toList();

        return new ProfSearchResponseDto(results, results.size());
    }

    // 3. 전공 검색
    public DeptSearchResponseDto searchDepartment(String keyword, Pageable pageable) {
        List<University> allUnivs = univRepository.findAll();

        List<DeptSearchResultDto> results = allUnivs.stream()
                .flatMap(univ -> univ.getColleges().stream()
                        .flatMap(college -> college.getDepartments().stream()
                                .filter(dept -> matchesSequentialChars(dept.getDeptName(), keyword))
                                .map(dept -> toDeptResult(dept, univ))
                        )
                )
                .sorted((d1, d2) -> compareRelevance(d1.name(), d2.name(), keyword))
                .skip(pageable.getOffset())
                .limit(pageable.getPageSize())
                .toList();

        return new DeptSearchResponseDto(results, results.size());
    }

    // 4. 대학 + 전공 검색
    public UnivDeptSearchResponseDto searchUnivAndDept(
            String univKeyword,
            String deptKeyword,
            Pageable pageable
    ) {

        List<University> matchedUnivs = univRepository.findAll().stream()
                .filter(u -> matchesSequentialChars(u.getName(), univKeyword))
                .toList();

        List<UnivDeptSearchResultDto> results = matchedUnivs.stream()
                .flatMap(univ -> univ.getColleges().stream()
                        .flatMap(college -> college.getDepartments().stream()
                                .filter(dept -> matchesSequentialChars(dept.getDeptName(), deptKeyword))
                                .map(dept -> toUnivDeptResult(univ, college, dept))
                        )
                )
                .skip(pageable.getOffset())
                .limit(pageable.getPageSize())
                .toList();

        return new UnivDeptSearchResponseDto(results, results.size());
    }

    // 5. 대학 + 교수 검색
    public UnivProfSearchResponseDto searchUnivAndProf(
            String univKeyword,
            String profKeyword,
            Pageable pageable
    ) {
        List<University> matchedUnivs = univRepository.findAll().stream()
                .filter(u -> matchesSequentialChars(u.getName(), univKeyword))
                .toList();

        List<UnivProfSearchResultDto> results = matchedUnivs.stream()
                .flatMap(univ -> univ.getColleges().stream()
                        .flatMap(college -> college.getDepartments().stream()
                                .flatMap(dept -> dept.getProfessors().stream()
                                        .filter(prof -> matchesSequentialChars(prof.getProfName(), profKeyword))
                                        .map(prof -> toUnivProfResult(univ, college, dept, prof))
                                )
                        )
                )
                .skip(pageable.getOffset())
                .limit(pageable.getPageSize())
                .toList();

        return new UnivProfSearchResponseDto(results, results.size());
    }

    private boolean matchesSequentialChars(String text, String query) {
        if (text == null || query.isEmpty()) return false;

        if (text.contains(query)) return true;

        int textIndex = 0;
        for (char c : query.toCharArray()) {
            textIndex = text.indexOf(c, textIndex);
            if (textIndex == -1) return false;
            textIndex++;
        }
        return true;
    }

    private int compareRelevance(String text1, String text2, String query) {
        return Integer.compare(
                getRelevanceScore(text2, query),
                getRelevanceScore(text1, query)
        );
    }

    private int getRelevanceScore(String text, String query) {
        if (text.equals(query)) return 1000;
        if (text.startsWith(query)) return 500;
        if (text.contains(query)) return 100;
        return 10;
    }

    // 1. 대학 dto 반환
    private UnivSearchResultDto toUnivResult(University univ) {
        Double rating = 4.2; // TODO: 대학 평점 계산
        Integer reviewCount = 1247; // TODO: 대학 리뷰 수 계산
        return UnivSearchResultDto.from(univ, rating, reviewCount);
    }

    // 2. 교수 dto 반환
    private ProfSearchResultDto toProfResult(Professor prof) {
        return ProfSearchResultDto.from(
                prof,
                calculateProfRating(prof.getProfSeq()),
                getProfReviewCount(prof.getProfSeq())
        );
    }

    // 3. 학과 dto 반환
    private DeptSearchResultDto toDeptResult(Department dept, University univ) {
        Double rating = 4.2; // TODO: 대학 평점 계산
        Integer reviewCount = 1247; // TODO: 대학 리뷰 수 계산
        return DeptSearchResultDto.from(dept, univ, rating, reviewCount);
    }

    // 4. 대학 + 학과 dto 반환
    private UnivDeptSearchResultDto toUnivDeptResult(
            University univ,
            College college,
            Department dept
    ) {
        Double rating = 4.5; // TODO: 대학 평점 계산
        Integer reviewCount = 150; // TODO: 대학 리뷰 수 계산
        return UnivDeptSearchResultDto.from(univ, college, dept, rating, reviewCount);
    }

    // 5. 대학 + 교수 dto 반환
    private UnivProfSearchResultDto toUnivProfResult(
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
                calculateProfRating(prof.getProfSeq()),
                getProfReviewCount(prof.getProfSeq())
        );
    }

    private Double calculateProfRating(Long profSeq) {
        Double rating = lectureReviewRepository.findOverallLectureRating(profSeq);
        return rating != null ? Math.round(rating * 10) / 10.0 : 0.0;
    }

    private Integer getProfReviewCount(Long profSeq) {
        return lectureReviewRepository.countByProfessorId(profSeq);
    }
}
