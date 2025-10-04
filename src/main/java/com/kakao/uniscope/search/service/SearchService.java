package com.kakao.uniscope.search.service;

import com.kakao.uniscope.college.entity.College;
import com.kakao.uniscope.department.entity.Department;
import com.kakao.uniscope.lecture.review.repository.LectureReviewRepository;
import com.kakao.uniscope.professor.entity.Professor;
import com.kakao.uniscope.professor.repository.ProfessorRepository;
import com.kakao.uniscope.search.algorithm.SearchAlgorithm;
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
import com.kakao.uniscope.search.mapper.SearchResultMapper;
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
    private final SearchAlgorithm searchAlgorithm;
    private final SearchResultMapper resultMapper;


    // 1. 대학 검색
    public UnivSearchResponseDto searchUniversity(String keyword, Pageable pageable) {
        List<University> allUnivs = univRepository.findAll();

        List<UnivSearchResultDto> results = allUnivs.stream()
                .filter(univ -> searchAlgorithm.matchesSequentialChars(univ.getName(), keyword))
                .sorted((u1, u2) -> searchAlgorithm.compareRelevance(u1.getName(), u2.getName(), keyword))
                .skip(pageable.getOffset())
                .limit(pageable.getPageSize())
                .map(resultMapper::toUnivResult)
                .toList();

        return new UnivSearchResponseDto(results, results.size());
    }

    // 2. 교수 검색
    public ProfSearchResponseDto searchProfessor(String keyword, Pageable pageable) {
        List<Professor> allProfs = professorRepository.findAll();

        List<ProfSearchResultDto> results = allProfs.stream()
                .filter(prof -> searchAlgorithm.matchesSequentialChars(prof.getProfName(), keyword))
                .sorted((p1, p2) -> searchAlgorithm.compareRelevance(p1.getProfName(), p2.getProfName(), keyword))
                .skip(pageable.getOffset())
                .limit(pageable.getPageSize())
                .map(resultMapper::toProfResult)
                .toList();

        return new ProfSearchResponseDto(results, results.size());
    }

    // 3. 전공 검색
    public DeptSearchResponseDto searchDepartment(String keyword, Pageable pageable) {
        List<University> allUnivs = univRepository.findAll();

        List<DeptSearchResultDto> results = allUnivs.stream()
                .flatMap(univ -> univ.getColleges().stream()
                        .flatMap(college -> college.getDepartments().stream()
                                .filter(dept -> searchAlgorithm.matchesSequentialChars(dept.getDeptName(), keyword))
                                .map(dept -> resultMapper.toDeptResult(dept, univ))
                        )
                )
                .sorted((d1, d2) -> searchAlgorithm.compareRelevance(d1.name(), d2.name(), keyword))
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
                .filter(u -> searchAlgorithm.matchesSequentialChars(u.getName(), univKeyword))
                .toList();

        List<UnivDeptSearchResultDto> results = matchedUnivs.stream()
                .flatMap(univ -> univ.getColleges().stream()
                        .flatMap(college -> college.getDepartments().stream()
                                .filter(dept -> searchAlgorithm.matchesSequentialChars(dept.getDeptName(), deptKeyword))
                                .map(dept -> resultMapper.toUnivDeptResult(univ, college, dept))
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
                .filter(u -> searchAlgorithm.matchesSequentialChars(u.getName(), univKeyword))
                .toList();

        List<UnivProfSearchResultDto> results = matchedUnivs.stream()
                .flatMap(univ -> univ.getColleges().stream()
                        .flatMap(college -> college.getDepartments().stream()
                                .flatMap(dept -> dept.getProfessors().stream()
                                        .filter(prof -> searchAlgorithm.matchesSequentialChars(prof.getProfName(), profKeyword))
                                        .map(prof -> resultMapper.toUnivProfResult(univ, college, dept, prof))
                                )
                        )
                )
                .skip(pageable.getOffset())
                .limit(pageable.getPageSize())
                .toList();

        return new UnivProfSearchResponseDto(results, results.size());
    }
}
