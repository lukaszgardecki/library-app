package com.example.libraryapp.domain.exception;

import com.example.libraryapp.domain.exception.book.BookNotFoundException;
import com.example.libraryapp.domain.exception.bookItem.BookItemException;
import com.example.libraryapp.domain.exception.bookItem.BookItemNotFoundException;
import com.example.libraryapp.domain.exception.fine.UnsettledFineException;
import com.example.libraryapp.domain.exception.lending.CheckoutException;
import com.example.libraryapp.domain.exception.lending.LendingNotFoundException;
import com.example.libraryapp.domain.exception.member.MemberHasNotReturnedBooksException;
import com.example.libraryapp.domain.exception.member.MemberNotFoundException;
import com.example.libraryapp.domain.exception.rack.RackException;
import com.example.libraryapp.domain.exception.rack.RackNotFoundException;
import com.example.libraryapp.domain.exception.reservation.ReservationException;
import com.example.libraryapp.domain.exception.reservation.ReservationNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler({
            MemberNotFoundException.class,
            BookNotFoundException.class,
            BookItemNotFoundException.class,
            ReservationNotFoundException.class,
            LendingNotFoundException.class,
            RackNotFoundException.class
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage sourceDoesNotExist(RuntimeException ex, WebRequest request) {
        return createErrorMessage(HttpStatus.NOT_FOUND,ex, request);
    }

    @ExceptionHandler({
            UnsettledFineException.class,
            MemberHasNotReturnedBooksException.class,
            ReservationException.class,
            CheckoutException.class,
            BookItemException.class,
            RackException.class
    })
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorMessage activityMustBeInterrupted(RuntimeException ex, WebRequest request) {
        return createErrorMessage(HttpStatus.CONFLICT,ex, request);
    }

    @ExceptionHandler({
            AccessDeniedException.class,
            BadCredentialsException.class
    })
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorMessage userHasNoAccessToData(RuntimeException ex, WebRequest request) {
        return createErrorMessage(HttpStatus.FORBIDDEN,ex, request);
    }

    @ExceptionHandler({
            MethodArgumentTypeMismatchException.class,
            HttpMessageNotReadableException.class
    })
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
