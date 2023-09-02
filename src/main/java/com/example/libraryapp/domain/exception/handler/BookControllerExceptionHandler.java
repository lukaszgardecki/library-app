package com.example.libraryapp.domain.exception.handler;

import com.example.libraryapp.domain.exception.BookIsNotAvailableException;
import com.example.libraryapp.domain.exception.BookNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class BookControllerExceptionHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> mismatchExceptionHandler() {
        return ResponseEntity.badRequest().body("Id must be a number.");
    }

    @ExceptionHandler(value = {BookNotFoundException.class, NullPointerException.class})
    public ResponseEntity<String> bookNotFoundExceptionHandler() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(BookIsNotAvailableException.class)
    public ResponseEntity<String> bookIsNotAvailableExceptionHandler(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                .header("Reason", e.getMessage())
                .build();
    }
}
