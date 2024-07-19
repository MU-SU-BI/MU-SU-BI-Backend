package com.musubi.domain.user.dto;


import com.musubi.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class UserSingUpDto {
    private final String email;
    private final String password;
    private final String phoneNumber;
    private final String homeAddress;
}
