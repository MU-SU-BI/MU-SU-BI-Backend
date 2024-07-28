package com.musubi.domain.user.dto;

import com.musubi.domain.location.domain.Location;
import com.musubi.domain.user.domain.User;
import com.musubi.domain.user.type.SexType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ConnectionResponseDto {
    private Long id;
    private String email;
    private String name;
    private int age;
    private SexType sex;
    private String nickName;
    private String homeAddress;
    private String district;

    public static ConnectionResponseDto fromEntity(User user) {
        return ConnectionResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .age(user.getAge())
                .sex(user.getSex())
                .nickName(user.getNickname())
                .homeAddress(user.getHomeAddress())
                .district(existLocation(user))
                .build();
    }

    private static String existLocation(User user) {
        try {
            return user.getLocation().getDistrict();
        } catch (Exception e) {
            return null;
        }
    }
}
