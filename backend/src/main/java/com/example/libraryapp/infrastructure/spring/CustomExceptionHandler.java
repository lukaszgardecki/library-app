package com.example.libraryapp.infrastructure.spring;

import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.auth.exceptions.ForbiddenAccessException;
import com.example.libraryapp.domain.book.exceptions.BookNotFoundException;
import com.example.libraryapp.domain.bookitem.exceptions.BookItemException;
import com.example.libraryapp.domain.bookitem.exceptions.BookItemNotFoundException;
import com.example.libraryapp.domain.bookitemloan.exceptions.BookItemLoanException;
import com.example.libraryapp.domain.bookitemloan.exceptions.BookItemLoanNotFoundException;
import com.example.libraryapp.domain.bookitemrequest.exceptions.BookItemRequestException;
import com.example.libraryapp.domain.bookitemrequest.exceptions.BookItemRequestNotFoundException;
import com.example.libraryapp.domain.exception.LibraryAppException;
import com.example.libraryapp.domain.exception.LibraryAppNotFoundException;
import com.example.libraryapp.domain.fine.exceptions.FineAlreadyPaidException;
import com.example.libraryapp.domain.fine.exceptions.FineNotFoundException;
import com.example.libraryapp.domain.librarycard.exceptions.LibraryCardNotFoundException;
import com.example.libraryapp.domain.message.ports.MessageProviderPort;
import com.example.libraryapp.domain.notification.exceptions.NotificationNotFoundException;
import com.example.libraryapp.domain.payment.exceptions.PaymentNotFoundException;
import com.example.libraryapp.domain.person.exceptions.PersonNotFoundException;
import com.example.libraryapp.domain.rack.exceptions.RackException;
import com.example.libraryapp.domain.rack.exceptions.RackNotFoundException;
import com.example.libraryapp.domain.token.exceptions.TokenNotFoundException;
import com.example.libraryapp.domain.user.exceptions.EmailAlreadyExistsException;
import com.example.libraryapp.domain.user.exceptions.UnsettledFineException;
import com.example.libraryapp.domain.user.exceptions.UserHasNotReturnedBooksException;
import com.example.libraryapp.domain.user.exceptions.UserNotFoundException;
import com.example.libraryapp.domain.useractivity.exceptions.UserActivityNotFoundException;
import io.jsonwebtoken.JwtException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class CustomExceptionHandler {
    private final MessageProviderPort msgProvider;

    @Getter
    @RequiredArgsConstructor
    static class ErrorMessage {
        private final int statusCode;
        private final LocalDateTime dateTime;
        private final String message;
        private final String description;

        public ErrorMessage(HttpStatus status, String message, WebRequest request) {
            this.statusCode = status.value();
            this.dateTime = LocalDateTime.now();
            this.message = message;
            this.description = request.getDescription(false);
        }
    }

    @ExceptionHandler(JwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorMessage tokenException(RuntimeException ex, WebRequest request) {
        return new ErrorMessage(HttpStatus.UNAUTHORIZED, ex.getMessage(), request);
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
            TokenNotFoundException.class
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage sourceDoesNotExist(LibraryAppNotFoundException ex, WebRequest request) {
        String message = msgProvider.getMessage(ex.getMessageKey(), ex.getSourceId());
        return new ErrorMessage(HttpStatus.NOT_FOUND, message, request);
    }

    @ExceptionHandler({
            BookItemException.class,
            BookItemRequestException.class,
            BookItemLoanException.class,
            FineAlreadyPaidException.class,
            UnsettledFineException.class,
            UserHasNotReturnedBooksException.class,
            RackException.class
    })
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorMessage activityMustBeInterrupted(LibraryAppException ex, WebRequest request) {
        String message = msgProvider.getMessage(ex.getMessageKey());
        return new ErrorMessage(HttpStatus.CONFLICT, message, request);
    }

    @ExceptionHandler({
            AccessDeniedException.class,
            BadCredentialsException.class,
            EmailAlreadyExistsException.class
    })
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorMessage userNotAuthenticated(RuntimeException ex, WebRequest request) {
        String message = "Undefined exception";
        if (ex instanceof AccessDeniedException) {
            message = msgProvider.getMessage(MessageKey.ACCESS_DENIED);
        } else if (ex instanceof BadCredentialsException) {
            message = msgProvider.getMessage(MessageKey.VALIDATION_BAD_CREDENTIALS);
        } else if (ex instanceof EmailAlreadyExistsException e) {
            message = msgProvider.getMessage(e.getMessageKey());
        }
        return new ErrorMessage(HttpStatus.UNAUTHORIZED, message, request);
    }

    @ExceptionHandler({
            ForbiddenAccessException.class,
            LockedException.class,
            DisabledException.class
    })
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorMessage userHasNoAccessToData(RuntimeException ex, WebRequest request) {
        String message;
        if (ex instanceof ForbiddenAccessException e) {
            message = msgProvider.getMessage(e.getMessageKey());
        } else {
            message = ex.getMessage();
        }
        return new ErrorMessage(HttpStatus.FORBIDDEN, message, request);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage requestBodyIsMissing(WebRequest request) {
        String message = msgProvider.getMessage(MessageKey.BODY_MISSING);
        return new ErrorMessage(HttpStatus.BAD_REQUEST, message, request);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage mismatchExceptionHandler(RuntimeException ex, WebRequest request) {
        return new ErrorMessage(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }
}
