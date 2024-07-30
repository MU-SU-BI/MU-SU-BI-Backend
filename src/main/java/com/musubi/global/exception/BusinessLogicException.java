package com.musubi.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BusinessLogicException extends RuntimeException {
    private String message;
    private int status;
}
