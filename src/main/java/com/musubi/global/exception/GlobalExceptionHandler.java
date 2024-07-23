package com.musubi.global.exception;

import com.musubi.global.utils.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler { //TODO : package 위치 이동 필요함
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> inputValidateException(MethodArgumentNotValidException ex) {

        String errorMessage = ex.getAllErrors().get(0).getDefaultMessage();

        ErrorResponse errorResponse = ErrorResponse.builder()
                .httpStatusCode(HttpStatus.BAD_REQUEST.value())
                .errorMessage(errorMessage)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
