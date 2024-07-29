package com.musubi.domain.location.dto;

import com.musubi.domain.location.dao.SafeAreaRepository;
import com.musubi.domain.location.domain.SafeArea;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
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

