package com.musubi.domain.user.exception;

import com.musubi.global.utils.ErrorMessage;
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
                .httpStatusCode(ErrorMessage.ALREADY_EXIST_USER_ERROR.getHttpStatusCode())
                .errorMessage(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(NotFoundUserException.class)
    public ResponseEntity<?> notFoundUserException(NotFoundUserException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .httpStatusCode(ErrorMessage.NOT_FOUND_USER_ERROR.getHttpStatusCode())
                .errorMessage(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(WrongPasswordException.class)
    public ResponseEntity<?> wrongPasswordException(WrongPasswordException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .httpStatusCode(ErrorMessage.WRONG_PASSWORD_ERROR.getHttpStatusCode())
                .errorMessage(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }
}
