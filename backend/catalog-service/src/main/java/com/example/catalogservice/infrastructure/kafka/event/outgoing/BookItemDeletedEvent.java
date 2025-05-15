package com.example.catalogservice.infrastructure.kafka.event.outgoing;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookItemDeletedEvent {
    private Long bookItemId;
    private Long bookId;
}
