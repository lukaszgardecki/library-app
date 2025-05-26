package com.example.notificationservice.infrastructure.kafka.event.incoming;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestReadyEvent {
    private Long userId;
    private Long bookItemId;
    private Long requestId;
    private String bookTitle;
}