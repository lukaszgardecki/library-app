package com.example.libraryapp.domain.exception.handler;

import com.example.libraryapp.domain.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ReservationControllerExceptionHandler {

    @ExceptionHandler(value = {
            BookNotFoundException.class,
            UserNotFoundException.class,
            ReservationNotFoundException.class
    })
    public ResponseEntity<String> resourceDoNotExist() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(BookIsNotAvailableException.class)
    public ResponseEntity<String> bookIsNotAvailable(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                .header("Reason", e.getMessage())
                .build();
    }

    @ExceptionHandler(value = {
            ReservationCannotBeCreatedException.class,
            ReservationCannotBeDeletedException.class
    })
    public ResponseEntity<String> reservationCannotBeCreated() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
