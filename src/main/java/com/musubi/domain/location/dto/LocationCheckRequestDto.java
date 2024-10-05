package com.musubi.domain.location.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public final class LocationCheckRequestDto {
    private String coordinate;
    private Long userId;
}
