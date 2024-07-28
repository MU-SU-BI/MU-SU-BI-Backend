package com.musubi.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public final class GuardianLoginRequestDto {
    private final String email;
    private final String password;
    private final String fcmToken;
}
