package com.example.libraryapp.domain.exception.handlers;

import com.example.libraryapp.domain.exception.bookItem.BookIsNotAvailableException;
import com.example.libraryapp.domain.exception.bookItem.BookNotFoundException;
import com.example.libraryapp.domain.exception.member.MemberNotFoundException;
import com.example.libraryapp.domain.exception.reservation.ReservationCannotBeDeletedException;
import com.example.libraryapp.domain.exception.reservation.ReservationNotFoundException;
import com.example.libraryapp.domain.exception.reservation.ResevationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ReservationControllerExceptionHandler {

    @ExceptionHandler(value = {
            BookNotFoundException.class,
            MemberNotFoundException.class,
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
            ResevationException.class,
            ReservationCannotBeDeletedException.class
    })
    public ResponseEntity<String> reservationCannotBeCreated() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
