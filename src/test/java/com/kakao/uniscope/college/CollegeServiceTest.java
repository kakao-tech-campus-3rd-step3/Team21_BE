package com.kakao.uniscope.college;

import com.kakao.uniscope.college.dto.CollegeResponseDto;
import com.kakao.uniscope.college.service.CollegeService;
import com.kakao.uniscope.common.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CollegeServiceTest {

    private CollegeService collegeService;

    @BeforeEach
    void setUp() {
        FakeCollegeRepository fakeRepository = new FakeCollegeRepository();
        this.collegeService = new CollegeService(fakeRepository);
    }

    @Test
    @DisplayName("존재하는 단과대학 seq로 학과 목록 조회 시, 학과 리스트가 정상 반환된다")
    void getDepartmentsByCollege_Success() {
        // given
        Long collegeSeq = 1L;

        // when
        CollegeResponseDto result = collegeService.getDepartmentsByCollege(collegeSeq);

        // then
        assertNotNull(result);
        assertEquals(2, result.departments().size());
    }

    @Test
    @DisplayName("존재하지 않는 단과대학 seq 조회 시, ResourceNotFoundException 발생")
    void getDepartmentsByCollege_NotFound() {
        // given
        Long collegeSeq = 999L;

        // when & then
        assertThrows(ResourceNotFoundException.class, () -> collegeService.getDepartmentsByCollege(collegeSeq));
    }
}
