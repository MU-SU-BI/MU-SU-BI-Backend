package com.musubi.domain.user.dto;


import com.musubi.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public final class UserLoginResponseDto {
    private final Long userId;
    private final String email;
    private final String name;
    private final String nickname;
    private final String phoneNumber;
    private final String homeAddress;
    private final String sex;

    public static UserLoginResponseDto fromEntity(User user) {
        return UserLoginResponseDto.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .nickname(user.getNickname())
                .sex(user.getSex().getValue())
                .phoneNumber(user.getPhoneNumber())
                .homeAddress(user.getHomeAddress())
                .build();
    }
}
