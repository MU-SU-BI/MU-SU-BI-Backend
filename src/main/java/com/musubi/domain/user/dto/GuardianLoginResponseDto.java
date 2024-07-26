package com.musubi.domain.user.dto;


import com.musubi.domain.user.domain.Guardian;
import com.musubi.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public final class GuardianLoginResponseDto {
    private final Long userId;
    private final String email;
    private final String name;
    private final String nickname;
    private final String phoneNumber;
    private final String homeAddress;
    private final String sex;

    public static GuardianLoginResponseDto fromEntity(Guardian guardian) {
        return GuardianLoginResponseDto.builder()
                .userId(guardian.getId())
                .email(guardian.getEmail())
                .name(guardian.getName())
                .nickname(guardian.getNickname())
                .sex(guardian.getSex().getValue())
                .phoneNumber(guardian.getPhoneNumber())
                .homeAddress(guardian.getHomeAddress())
                .build();
    }
}
