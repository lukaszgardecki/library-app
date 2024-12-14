package com.example.libraryapp.OLDdomain.exception;

import com.example.libraryapp.OLDdomain.exception.action.ActionNotFoundException;
import com.example.libraryapp.OLDdomain.exception.auth.ForbiddenAccessException;
import com.example.libraryapp.OLDdomain.exception.book.BookNotFoundException;
import com.example.libraryapp.OLDdomain.exception.bookItem.BookItemException;
import com.example.libraryapp.OLDdomain.exception.bookItem.BookItemNotFoundException;
import com.example.libraryapp.OLDdomain.exception.card.CardNotFoundException;
import com.example.libraryapp.OLDdomain.exception.lending.CheckoutException;
import com.example.libraryapp.OLDdomain.exception.lending.LendingNotFoundException;
import com.example.libraryapp.OLDdomain.exception.member.MemberHasNotReturnedBooksException;
import com.example.libraryapp.NEWdomain.user.exceptions.UserNotFoundException;
import com.example.libraryapp.OLDdomain.exception.notification.NotificationNotFoundException;
import com.example.libraryapp.OLDdomain.exception.payment.PaymentNotFoundException;
import com.example.libraryapp.OLDdomain.exception.payment.UnsettledFineException;
import com.example.libraryapp.OLDdomain.exception.rack.RackException;
import com.example.libraryapp.OLDdomain.exception.rack.RackNotFoundException;
import com.example.libraryapp.OLDdomain.exception.reservation.ReservationException;
import com.example.libraryapp.OLDdomain.exception.reservation.ReservationNotFoundException;
import com.example.libraryapp.OLDmanagement.Message;
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

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(JwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorMessage tokenException(RuntimeException ex, WebRequest request) {
        return new ErrorMessage(HttpStatus.UNAUTHORIZED,ex, request);
    }

    @ExceptionHandler({
            UserNotFoundException.class,
            BookNotFoundException.class,
            BookItemNotFoundException.class,
            ReservationNotFoundException.class,
            LendingNotFoundException.class,
            RackNotFoundException.class,
            CardNotFoundException.class,
            PaymentNotFoundException.class,
            ActionNotFoundException.class,
            NotificationNotFoundException.class
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage sourceDoesNotExist(RuntimeException ex, WebRequest request) {
        return new ErrorMessage(HttpStatus.NOT_FOUND,ex, request);
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
        return new ErrorMessage(HttpStatus.CONFLICT,ex, request);
    }

    @ExceptionHandler({
            AccessDeniedException.class,
            BadCredentialsException.class
    })
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorMessage userNotAuthenticated(RuntimeException ex, WebRequest request) {
        return new ErrorMessage(HttpStatus.UNAUTHORIZED,ex, request);
    }

    @ExceptionHandler({
            ForbiddenAccessException.class,
            LockedException.class,
            DisabledException.class
    })
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorMessage userHasNoAccessToData(RuntimeException ex, WebRequest request) {
        return new ErrorMessage(HttpStatus.FORBIDDEN,ex, request);
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
