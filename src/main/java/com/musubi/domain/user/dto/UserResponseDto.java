package com.musubi.domain.user.dto;


import com.musubi.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class UserResponseDto {
    private final Long userId;
    private final String email;
    private final String name;
    private final String nickname;
    private final int age;
    private final String sex;
    private final String homeAddress;
    private final String phoneNumber;
    private final byte[] profile;

    public static UserResponseDto fromEntity(User user) {
        return UserResponseDto.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .nickname(user.getNickname())
                .age(user.getAge())
                .sex(user.getSex().getValue())
                .phoneNumber(user.getPhoneNumber())
                .homeAddress(user.getHomeAddress())
                .profile(user.getProfile())
                .build();
    }
}
