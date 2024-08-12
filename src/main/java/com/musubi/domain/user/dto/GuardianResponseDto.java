package com.musubi.domain.user.dto;

import com.musubi.domain.user.domain.Guardian;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@AllArgsConstructor
@Getter
@Builder
public class GuardianResponseDto {
    private final Long guardianId;
    private final String email;
    private final String name;
    private final String nickname;
    private final int age;
    private final String sex;
    private final String homeAddress;
    private final String phoneNumber;

    public static GuardianResponseDto fromEntity(Guardian guardian) {
        return GuardianResponseDto.builder()
                .guardianId(guardian.getId())
                .email(guardian.getEmail())
                .name(guardian.getName())
                .nickname(guardian.getNickname())
                .age(guardian.getAge())
                .sex(guardian.getSex().getValue())
                .phoneNumber(guardian.getPhoneNumber())
                .homeAddress(guardian.getHomeAddress())
                .build();
    }
}
