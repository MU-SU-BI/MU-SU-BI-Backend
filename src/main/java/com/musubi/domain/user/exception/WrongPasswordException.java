package com.musubi.domain.user.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class WrongPasswordException extends RuntimeException {
    private final String message;
}
