package com.musubi.domain.location.dto;

import com.musubi.domain.location.domain.Location;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public final class LocationCheckResponseDto {

    public static String fromEntity(Location location) {
        return location.getDistrict();
    }
}
