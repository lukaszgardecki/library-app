package com.example.libraryapp.domain.exception.handlers;

import com.example.libraryapp.domain.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CheckoutControllerExceptionHandler {

    @ExceptionHandler(value = {
            UserNotFoundException.class,
            CheckoutNotFoundException.class,
            BookNotFoundException.class,
            ReservationNotFoundException.class

    })
    public ResponseEntity<String> sourceDoNotExist() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(value = CheckoutCannotBeCreatedException.class)
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
