package com.musubi.domain.location.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SafeAreaRequestDto {
    private Long userId;
    private Double longitude;
    private Double latitude;
    private Double radius;
}
