package com.musubi.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ConnectionRequestDto {
    private Long userId;
    private String disabledName;
    private String disabledPhoneNumber;
}
