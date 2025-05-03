package com.example.fineservice.infrastructure.http;

import com.example.fineservice.domain.MessageKey;
import com.example.fineservice.domain.exceptions.*;
import com.example.fineservice.domain.ports.MessageProviderPort;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;

@RestControllerAdvice
@RequiredArgsConstructor
class CustomExceptionHandler {
    private final MessageProviderPort msgProvider;

    @Getter
    @RequiredArgsConstructor
    private static class ErrorMessage {
        private final int statusCode;
        private final LocalDateTime dateTime;
        private final String message;
        private final String description;

        ErrorMessage(HttpStatus status, String message, WebRequest request) {
            this.statusCode = status.value();
            this.dateTime = LocalDateTime.now();
            this.message = message;
            this.description = request.getDescription(false);
        }
    }

    @ExceptionHandler(FineNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErrorMessage sourceDoesNotExist(LibraryAppNotFoundException ex, WebRequest request) {
        String message = msgProvider.getMessage(ex.getMessageKey(), ex.getSourceId());
        return new ErrorMessage(HttpStatus.NOT_FOUND, message, request);
    }

    @ExceptionHandler({
            FineAlreadyPaidException.class,
            UnsettledFineException.class
    })
    @ResponseStatus(HttpStatus.CONFLICT)
    ErrorMessage activityMustBeInterrupted(LibraryAppException ex, WebRequest request) {
        String message = msgProvider.getMessage(ex.getMessageKey());
        return new ErrorMessage(HttpStatus.CONFLICT, message, request);
    }


    @ExceptionHandler({
            AccessDeniedException.class,
            AuthenticationException.class
    })
    @ResponseStatus(HttpStatus.FORBIDDEN)
    ErrorMessage userHasNoAccessToData(RuntimeException ex, WebRequest request) {
        String message;
        if (ex instanceof AccessDeniedException) {
            message = msgProvider.getMessage(MessageKey.FORBIDDEN);
        } else {
            message = ex.getMessage();
        }
        return new ErrorMessage(HttpStatus.FORBIDDEN, message, request);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorMessage requestBodyIsMissing(WebRequest request) {
        String message = msgProvider.getMessage(MessageKey.BODY_MISSING);
        return new ErrorMessage(HttpStatus.BAD_REQUEST, message, request);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorMessage mismatchExceptionHandler(RuntimeException ex, WebRequest request) {
        return new ErrorMessage(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }
}
