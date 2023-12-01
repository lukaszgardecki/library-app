package com.example.libraryapp.domain.exception.handlers;

import com.example.libraryapp.domain.exception.bookItem.BookIsAlreadyReturnedException;
import com.example.libraryapp.domain.exception.bookItem.BookNotFoundException;
import com.example.libraryapp.domain.exception.lending.LendingCannotBeCreatedException;
import com.example.libraryapp.domain.exception.lending.LendingNotFoundException;
import com.example.libraryapp.domain.exception.member.MemberNotFoundException;
import com.example.libraryapp.domain.exception.reservation.ReservationNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class LendingControllerExceptionHandler {

    @ExceptionHandler(value = {
            MemberNotFoundException.class,
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
