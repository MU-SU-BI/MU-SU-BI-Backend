package com.musubi.global.utils.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@AllArgsConstructor
public class NaverMapResponseJsonDto {
    Status status;
    List<NaverMapResponseDto> results;
}
