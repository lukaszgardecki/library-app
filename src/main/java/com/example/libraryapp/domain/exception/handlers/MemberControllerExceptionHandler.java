package com.example.libraryapp.domain.exception.handlers;

import com.example.libraryapp.domain.exception.member.MemberHasNotReturnedBooksException;
import com.example.libraryapp.domain.exception.member.MemberNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class MemberControllerExceptionHandler {

    @ExceptionHandler(value = {
            MemberNotFoundException.class,
            NullPointerException.class
    })
    public ResponseEntity<String> sourceDoNotExist() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MemberHasNotReturnedBooksException.class)
    public ResponseEntity<String> userHasNotReturnedBook() {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                .header("Reason", "User's books are not returned.")
                .build();
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> mismatchExceptionHandler() {
        return ResponseEntity.badRequest().body("Id must be a number.");
    }
}
