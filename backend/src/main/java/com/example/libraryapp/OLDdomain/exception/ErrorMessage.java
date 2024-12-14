package com.example.libraryapp.OLDdomain.exception;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage {
    private int statusCode;
    private LocalDateTime dateTime;
    private String message;
    private String description;

    public ErrorMessage(HttpStatus status, RuntimeException ex, WebRequest request) {
        this.statusCode = status.value();
        this.dateTime = LocalDateTime.now();
        this.message = ex.getMessage();
        this.description = request.getDescription(false);
    }
}
