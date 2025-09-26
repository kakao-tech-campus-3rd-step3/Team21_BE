package com.kakao.uniscope.univ;

import com.kakao.uniscope.college.dto.CollegeListResponseDto;
import com.kakao.uniscope.common.exception.ResourceNotFoundException;
import com.kakao.uniscope.univ.dto.UnivResponseDto;
import com.kakao.uniscope.univ.service.UnivService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UnivServiceTest {

    private UnivService univService;

    @BeforeEach
    void setUp() {
        FakeUnivRepository fakeUnivRepository = new FakeUnivRepository();
        univService = new UnivService(fakeUnivRepository);
    }

    @Test
    @DisplayName("대학교 상세 정보 조회 시, 정보가 정상 반환된다")
    void getUniversityDetails_ReturnsDataSuccessfully() {
        // given
        Long univSeq = 1L;

        // when
        UnivResponseDto result = univService.getUniversityInfo(univSeq);

        // then
        assertNotNull(result);
        assertEquals("충남대학교", result.university().name());
    }

    @Test
    @DisplayName("존재하지 않는 대학교 조회 시 ResourceNotFoundException 발생")
    void getUniversityDetails_NotFound() {
        assertThrows(ResourceNotFoundException.class,
                () -> univService.getUniversityInfo(999L));
    }

    @Test
    void 대학의_단과대학_목록을_조회하면_모든_단과대학을_가져온다() {
        Long univSeq = 1L;

        CollegeListResponseDto result = univService.getAllCollegeList(univSeq);

        assertNotNull(result);
        assertEquals(3, result.colleges().size());
    }
}
