package com.example.libraryapp.infrastructure.spring;

import com.example.libraryapp.domain.auth.exceptions.ForbiddenAccessException;
import com.example.libraryapp.domain.book.exceptions.BookNotFoundException;
import com.example.libraryapp.domain.bookitem.exceptions.BookItemNotFoundException;
import com.example.libraryapp.domain.bookitemloan.exceptions.BookItemLoanException;
import com.example.libraryapp.domain.bookitemloan.exceptions.BookItemLoanNotFoundException;
import com.example.libraryapp.domain.bookitemrequest.exceptions.BookItemRequestException;
import com.example.libraryapp.domain.bookitemrequest.exceptions.BookItemRequestNotFoundException;
import com.example.libraryapp.domain.fine.exceptions.FineAlreadyPaidException;
import com.example.libraryapp.domain.fine.exceptions.FineNotFoundException;
import com.example.libraryapp.domain.librarycard.exceptions.LibraryCardNotFoundException;
import com.example.libraryapp.domain.notification.exceptions.NotificationNotFoundException;
import com.example.libraryapp.domain.payment.exceptions.PaymentNotFoundException;
import com.example.libraryapp.domain.person.exceptions.PersonNotFoundException;
import com.example.libraryapp.domain.rack.exceptions.RackException;
import com.example.libraryapp.domain.rack.exceptions.RackNotFoundException;
import com.example.libraryapp.domain.user.exceptions.MemberHasNotReturnedBooksException;
import com.example.libraryapp.domain.user.exceptions.UnsettledFineException;
import com.example.libraryapp.domain.user.exceptions.UserNotFoundException;
import com.example.libraryapp.domain.useractivity.exceptions.UserActivityNotFoundException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class CustomExceptionHandler {

    private class ErrorMessage {
        private final int statusCode;
        private final LocalDateTime dateTime;
        private final String message;
        private final String description;

        public ErrorMessage(HttpStatus status, RuntimeException ex, WebRequest request) {
            this.statusCode = status.value();
            this.dateTime = LocalDateTime.now();
            this.message = ex.getMessage();
            this.description = request.getDescription(false);
        }
    }


    @ExceptionHandler(JwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorMessage tokenException(RuntimeException ex, WebRequest request) {
        return new ErrorMessage(HttpStatus.UNAUTHORIZED, ex, request);
    }

    @ExceptionHandler({
            UserNotFoundException.class,
            BookNotFoundException.class,
            BookItemNotFoundException.class,
            BookItemRequestNotFoundException.class,
            BookItemLoanNotFoundException.class,
            LibraryCardNotFoundException.class,
            NotificationNotFoundException.class,
            PersonNotFoundException.class,
            RackNotFoundException.class,
            FineNotFoundException.class,
            PaymentNotFoundException.class,
            UserActivityNotFoundException.class,
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage sourceDoesNotExist(RuntimeException ex, WebRequest request) {
        return new ErrorMessage(HttpStatus.NOT_FOUND, ex, request);
    }

    @ExceptionHandler({
            BookItemRequestException.class,
            BookItemLoanException.class,
            FineAlreadyPaidException.class,
            UnsettledFineException.class,
            MemberHasNotReturnedBooksException.class,
            RackException.class
    })
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorMessage activityMustBeInterrupted(RuntimeException ex, WebRequest request) {
        return new ErrorMessage(HttpStatus.CONFLICT, ex, request);
    }

    @ExceptionHandler({
            AccessDeniedException.class,
            BadCredentialsException.class
    })
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorMessage userNotAuthenticated(RuntimeException ex, WebRequest request) {
        return new ErrorMessage(HttpStatus.UNAUTHORIZED, ex, request);
    }

    @ExceptionHandler({
            ForbiddenAccessException.class,
            LockedException.class,
            DisabledException.class
    })
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorMessage userHasNoAccessToData(RuntimeException ex, WebRequest request) {
        return new ErrorMessage(HttpStatus.FORBIDDEN, ex, request);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage requestBodyIsMissing(WebRequest request) {
        return new ErrorMessage(
                HttpStatus.BAD_REQUEST, new HttpMessageNotReadableException("Message.BODY_MISSING.getMessage()"), request
        );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage mismatchExceptionHandler(RuntimeException ex, WebRequest request) {
        return new ErrorMessage(HttpStatus.BAD_REQUEST, ex, request);
    }
}
