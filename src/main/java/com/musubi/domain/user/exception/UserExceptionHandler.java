package com.musubi.domain.user.exception;

import com.musubi.global.utils.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionHandler {
    @ExceptionHandler(AlreadyExistEmailException.class)
    public ResponseEntity<?> alreadyExistUserExceptionHandler(AlreadyExistEmailException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .httpStatusCode(HttpStatus.CONFLICT.value())
                .errorMessage(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(AlreadyExistNicknameException.class)
    public ResponseEntity<?> alreadyExistNickNameException(AlreadyExistNicknameException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .httpStatusCode(HttpStatus.CONFLICT.value())
                .errorMessage(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(AlreadyExistPhoneNumberException.class)
    public ResponseEntity<?> alreadyExistPhoneNumberException(AlreadyExistPhoneNumberException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .httpStatusCode(HttpStatus.CONFLICT.value())
                .errorMessage(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(NoneExistConnectException.class)
    public ResponseEntity<?> noneExistConnectException(NoneExistConnectException ex) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .httpStatusCode(HttpStatus.BAD_REQUEST.value())
                .errorMessage(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }


    @ExceptionHandler(NotFoundUserException.class)
    public ResponseEntity<?> notFoundUserException(NotFoundUserException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .httpStatusCode(HttpStatus.UNAUTHORIZED.value())
                .errorMessage(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(WrongPasswordException.class)
    public ResponseEntity<?> wrongPasswordException(WrongPasswordException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .httpStatusCode(HttpStatus.UNAUTHORIZED.value())
                .errorMessage(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }
}
