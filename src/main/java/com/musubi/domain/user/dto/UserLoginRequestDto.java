package com.musubi.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public final class UserLoginRequestDto {
    private final String email;
    private final String password;
}
