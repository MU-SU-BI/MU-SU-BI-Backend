package com.musubi.domain.user.dto;

import com.musubi.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
public class MissingUserResponseDto {
    private final String name;
    private final int age;
    private final String sex;
    private final String homeAddress;
    private final String phoneNumber;

    private final String guardianPhoneNumber;

    private final Double longitude;
    private final Double latitude;
    private final String profile;

    public static MissingUserResponseDto fromEntity(User user) {
        return MissingUserResponseDto.builder()
                .name(user.getName())
                .age(user.getAge())
                .sex(user.getSex().getValue())
                .homeAddress(user.getHomeAddress())
                .phoneNumber(user.getPhoneNumber())
                .guardianPhoneNumber((user.getGuardian() != null) ? user.getGuardian().getPhoneNumber() : null)
                .longitude((user.getCurrentLocation() != null) ? user.getCurrentLocation().getNowLongitude() : null)
                .latitude((user.getCurrentLocation() != null) ? user.getCurrentLocation().getNowLatitude() : null)
                .profile(user.getProfile())
                .build();
    }
}
