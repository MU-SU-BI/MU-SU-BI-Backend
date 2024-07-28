package com.musubi.domain.location.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CurrentLocationRequestDto {
    private Long userId;
    private double latitude;
    private double longitude;
}
