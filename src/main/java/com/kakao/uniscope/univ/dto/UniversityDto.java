package com.kakao.uniscope.univ.dto;

import com.kakao.uniscope.univ.entity.University;

public record UniversityDto(
        Long univSeq,
        String name,
        String address,
        String tel,
        String homePage,
        String image,
        String establishedYear,
        Integer totalStudent
) {
    public static UniversityDto from(University univ) {
        return new UniversityDto(
                univ.getUnivSeq(),
                univ.getName(),
                univ.getAddress(),
                univ.getTel(),
                univ.getHomePage(),
                univ.getImageUrl(),
                univ.getEstablishedYear(),
                univ.getTotalStudent()
        );
    }
}
