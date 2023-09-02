package com.example.libraryapp.domain.exception.handler;

import com.example.libraryapp.domain.exception.UserHasNotReturnedBooksException;
import com.example.libraryapp.domain.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class UserControllerExceptionHandler {

    @ExceptionHandler(value = {
            UserNotFoundException.class,
            NullPointerException.class
    })
    public ResponseEntity<String> sourceDoNotExist() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(UserHasNotReturnedBooksException.class)
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
