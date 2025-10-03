package com.kakao.uniscope.search.dto;

import com.kakao.uniscope.univ.entity.University;

public record UnivSearchResultDto(
        Long univSeq,
        String name,
        String address,
        String imageUrl,
        Double rating,
        Integer reviewCount
) {
    public static UnivSearchResultDto from(University univ, Double rating, Integer reviewCount) {
        return new UnivSearchResultDto(
                univ.getUnivSeq(),
                univ.getName(),
                univ.getAddress(),
                univ.getImageUrl(),
                rating,
                reviewCount
        );
    }
}
