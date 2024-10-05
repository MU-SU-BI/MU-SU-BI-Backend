package com.musubi.domain.location.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class SafeAreaResponseDto {
    private double longitude;
    private double latitude;
    private double radius;

}

