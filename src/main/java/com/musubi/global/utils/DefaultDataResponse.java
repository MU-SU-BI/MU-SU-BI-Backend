package com.musubi.global.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class DefaultDataResponse<T> {
    private final int httpStatusCode;
    private final String responseMessage;
    private final T data;
}
