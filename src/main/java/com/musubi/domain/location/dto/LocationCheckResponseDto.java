package com.musubi.domain.location.dto;

import com.musubi.domain.location.domain.Location;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class LocationCheckResponseDto {
    private String district;

    public static LocationCheckResponseDto fromEntity(Location location) {
        return LocationCheckResponseDto.builder()
                .district(location.getDistrict())
                .build();
    }
}
