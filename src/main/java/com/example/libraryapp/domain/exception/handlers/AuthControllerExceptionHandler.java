package com.example.libraryapp.domain.exception.handlers;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AuthControllerExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> badCredentials() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> dataIntegrityViolation() {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                .header("Reason", "User with this email already exists")
                .build();
    }
}
