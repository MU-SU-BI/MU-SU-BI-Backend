package com.musubi.domain.location.dto;

import com.musubi.domain.location.domain.Location;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public final class LocationCheckResponseDto {
    // private final Long userId;
   // private final String coordinate;
   // private final String district;

    public static String fromEntity(Location location) {
        return location.getDistrict();
        //return LocationCheckResponseDto.builder()
        //        .userId(location.getId())
        //        .coordinate(location.getCoordinate())
        //        .district(location.getDistrict())
        //        .build();
    }
}
