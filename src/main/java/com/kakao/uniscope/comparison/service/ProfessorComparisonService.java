package com.kakao.uniscope.comparison.service;

import com.kakao.uniscope.common.exception.ResourceNotFoundException;
import com.kakao.uniscope.comparison.dto.ProfessorComparisonDto;
import com.kakao.uniscope.comparison.exception.ComparisonException;
import com.kakao.uniscope.professor.entity.Professor;
import com.kakao.uniscope.professor.repository.ProfessorRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProfessorComparisonService {

    private final ProfessorRepository professorRepository;
    private final ProfessorComparisonMapper professorComparisonMapper;

    public List<ProfessorComparisonDto> getProfessorsComparisonData(List<Long> profSeqs) {
        validateProfSeqs(profSeqs);

        List<Professor> professors = findProfessors(profSeqs);

        return professors.stream()
                .map(professorComparisonMapper::toComparisonDto)
                .collect(Collectors.toList());
    }


    private void validateProfSeqs(List<Long> profSeqs) {
        if (profSeqs == null || profSeqs.isEmpty() || profSeqs.size() > 3) {
            throw new ComparisonException("비교할 교수 ID는 1개 이상 3개 이하로 제공되어야 합니다.");
        }
    }

    private List<Professor> findProfessors(List<Long> profSeqs) {
        List<Professor> professors = professorRepository.findAllById(profSeqs);

        if (professors.size() != profSeqs.size()) {
            throw new ResourceNotFoundException("요청된 일부 교수 정보를 찾을 수 없거나 존재하지 않습니다.");
        }

        return professors;
    }
}