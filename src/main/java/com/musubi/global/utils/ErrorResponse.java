package com.musubi.global.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public final class ErrorResponse {
    private final int httpStatusCode;
    private final String errorMessage;
}
