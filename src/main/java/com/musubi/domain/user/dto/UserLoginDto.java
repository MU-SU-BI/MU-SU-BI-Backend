package com.musubi.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class UserLoginDto {
    private final String email;
    private final String password;
}
