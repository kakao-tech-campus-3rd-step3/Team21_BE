package com.kakao.uniscope.univ.service;

import com.kakao.uniscope.college.dto.CollegeListResponseDto;
import com.kakao.uniscope.college.dto.CollegeSummaryDto;
import com.kakao.uniscope.common.exception.ResourceNotFoundException;
import com.kakao.uniscope.univ.dto.UnivResponseDto;
import com.kakao.uniscope.univ.dto.UniversityDto;
import com.kakao.uniscope.univ.entity.University;
import com.kakao.uniscope.univ.repository.UnivRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UnivService {

    private final UnivRepository univRepository;

    public UnivService(UnivRepository univRepository) {
        this.univRepository = univRepository;
    }

    @Transactional(readOnly = true)
    public UnivResponseDto getUniversityInfo(Long univSeq) {
        University university = univRepository.findById(univSeq)
                .orElseThrow(() -> new ResourceNotFoundException(univSeq + "에 해당하는 대학교를 찾을 수 없습니다."));

        return new UnivResponseDto(UniversityDto.from(university));
    }

    @Transactional(readOnly = true)
    public CollegeListResponseDto getAllCollegeList(Long univSeq) {
        University university = univRepository.findWithCollegesByUnivSeq(univSeq)
                .orElseThrow(() -> new ResourceNotFoundException(univSeq + "에 해당하는 대학교를 찾을 수 없습니다."));

        List<CollegeSummaryDto> collegeDtos = university.getColleges().stream()
                .map(CollegeSummaryDto::from)
                .toList();

        return new CollegeListResponseDto(collegeDtos);
    }

}
