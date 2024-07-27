package com.musubi.domain.user.type;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserType {
    USER("user"), GUARDIAN("guardian");

    private final String value;

    @JsonCreator
    public static UserType parsing(String inputValue) {
        return Stream.of(UserType.values())
                .filter(user -> user.toString().equals(inputValue.toUpperCase()))
                .findFirst()
                .orElse(null);
    }
}
