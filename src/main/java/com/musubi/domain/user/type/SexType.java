package com.musubi.domain.user.type;


import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum SexType {
    MAlE("남"), FEMALE("여");

    private final String value;
}
