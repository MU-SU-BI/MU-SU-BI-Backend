package com.musubi.domain.location.dto;

import com.musubi.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CurrentLocationResponseDto {
    private double longitude;
    private double latitude;

    public static CurrentLocationResponseDto fromEntity(User user) {
        return CurrentLocationResponseDto.builder()
                .latitude(user.getCurrentLocation().getNowLatitude())
                .longitude(user.getCurrentLocation().getNowLongitude())
                .build();
    }
}
