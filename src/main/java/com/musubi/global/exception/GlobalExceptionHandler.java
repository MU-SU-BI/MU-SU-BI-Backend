package com.musubi.global.exception;

import com.musubi.global.utils.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
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

    @ExceptionHandler(BusinessLogicException.class)
    public ResponseEntity<?> businessLogicExceptionHandler(BusinessLogicException ex) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .httpStatusCode(ex.getStatus())
                .errorMessage(ex.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(ex.getStatus()));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<?> missingServletRequestParameterException(MissingServletRequestParameterException ex) {

        ErrorResponse errorResponse = ErrorResponse.builder()
            .httpStatusCode(HttpStatus.BAD_REQUEST.value())
            .errorMessage("요청 파라미터가 누적 되었습니다. : " + ex.getParameterName())
            .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
