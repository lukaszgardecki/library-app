package com.example.libraryapp.domain.exception.handlers;

import com.example.libraryapp.domain.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class LendingControllerExceptionHandler {

    @ExceptionHandler(value = {
            UserNotFoundException.class,
            LendingNotFoundException.class,
            BookNotFoundException.class,
            ReservationNotFoundException.class

    })
    public ResponseEntity<String> sourceDoNotExist() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(value = LendingCannotBeCreatedException.class)
    public ResponseEntity<String> sourceCannotBeCreated() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @ExceptionHandler(value = BookIsAlreadyReturnedException.class)
    public ResponseEntity<String> bookIsAlreadyReturned(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                .header("Reason", e.getMessage())
                .build();
    }
}
