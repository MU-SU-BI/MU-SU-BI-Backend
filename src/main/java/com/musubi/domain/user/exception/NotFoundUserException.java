package com.musubi.domain.user.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class NotFoundUserException extends RuntimeException {
    private final String message;
}
