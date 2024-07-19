package com.musubi.global.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class DefaultResponse {
    private final int httpStatusCode;
    private final String responseMessage;
}
