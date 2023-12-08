package com.example.libraryapp.domain.exception;

import com.example.libraryapp.domain.exception.bookItem.BookItemNotFoundException;
import com.example.libraryapp.domain.exception.bookItem.BookNotFoundException;
import com.example.libraryapp.domain.exception.fine.UnsettledFineException;
import com.example.libraryapp.domain.exception.lending.CheckoutException;
import com.example.libraryapp.domain.exception.lending.LendingNotFoundException;
import com.example.libraryapp.domain.exception.member.MemberHasNotReturnedBooksException;
import com.example.libraryapp.domain.exception.member.MemberNotFoundException;
import com.example.libraryapp.domain.exception.reservation.ReservationException;
import com.example.libraryapp.domain.exception.reservation.ReservationNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler({
            MemberNotFoundException.class,
            BookNotFoundException.class,
            BookItemNotFoundException.class,
            ReservationNotFoundException.class,
            LendingNotFoundException.class
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage sourceDoesNotExist(RuntimeException ex, WebRequest request) {
        return createErrorMessage(HttpStatus.NOT_FOUND,ex, request);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({
            UnsettledFineException.class,
            MemberHasNotReturnedBooksException.class,
            ReservationException.class,
            CheckoutException.class
    })
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorMessage sourceDoesNotEklxist(RuntimeException ex, WebRequest request) {
        // TODO: 06.12.2023 zmienić nazwę metody
        return createErrorMessage(HttpStatus.CONFLICT,ex, request);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorMessage userHasNoAccessToData(RuntimeException ex, WebRequest request) {
        return createErrorMessage(HttpStatus.FORBIDDEN,ex, request);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage mismatchExceptionHandler(RuntimeException ex, WebRequest request) {
        return createErrorMessage(HttpStatus.BAD_REQUEST, ex, request);
    }

    private ErrorMessage createErrorMessage(HttpStatus status, RuntimeException ex, WebRequest request) {
        return ErrorMessage.builder()
                .statusCode(status.value())
                .dateTime(LocalDateTime.now())
                .message(ex.getMessage())
                .description(request.getDescription(false))
                .build();
    }
}
