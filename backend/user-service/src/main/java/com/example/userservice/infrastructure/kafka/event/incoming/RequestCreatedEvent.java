package com.example.userservice.infrastructure.kafka.event.incoming;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestCreatedEvent {
    private Long requestId;
    private LocalDateTime requestCreationDate;
    private String requestStatus;
    private Long bookItemId;
    private Long userId;
    private String bookTitle;
}


