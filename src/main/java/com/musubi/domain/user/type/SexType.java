package com.musubi.domain.user.type;


import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum SexType {
    MALE("남"), FEMALE("여");

    private final String value;

    @JsonCreator
    public static SexType parsing(String inputValue) {
        return Stream.of(SexType.values())
                .filter(sex -> sex.toString().equals(inputValue.toUpperCase()))
                .findFirst()
                .orElse(null);
    }
}
