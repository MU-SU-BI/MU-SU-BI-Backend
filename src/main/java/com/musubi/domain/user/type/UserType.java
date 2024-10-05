package com.musubi.domain.user.type;

import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserType {
    USER("user"), GUARDIAN("guardian");

    private final String value;

    public static UserType of(String inputValue) {
        return Stream.of(UserType.values())
                .filter(user -> user.toString().equals(inputValue.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("일치하는 Type이 없습니다."));
    }
}
