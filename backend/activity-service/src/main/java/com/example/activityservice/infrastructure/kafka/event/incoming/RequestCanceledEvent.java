package com.example.activityservice.infrastructure.kafka.event.incoming;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestCanceledEvent {
    private Long bookItemId;
    private Long userId;
    private Long bookId;
    private String bookTitle;
}
