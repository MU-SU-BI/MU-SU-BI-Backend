package com.musubi.domain.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FCMRequestDto {
    private Long userId;
    private String targetToken;
}

