package com.musubi.global.utils.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@AllArgsConstructor
@Builder
public class NaverMapResponseDto {
    String name;
    Code code;
    Region region;
}
