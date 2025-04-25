package com.example.loanservice.infrastructure.events;

import com.example.loanservice.domain.event.incoming.BookItemReservedEvent;
import com.example.loanservice.domain.ports.EventListenerPort;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class EventListenerAdapter {
    private final EventListenerPort eventListener;

    private static final String BOOK_ITEM_LOANED_SUCCESS_TOPIC = "book-item.loaned.success";

    @KafkaListener(topics = BOOK_ITEM_LOANED_SUCCESS_TOPIC, groupId = "request-service-listeners")
    void bookItemLoaned(BookItemReservedEvent event) {
        eventListener.handleBookItemReservedEvent(event.getBookItemId());
    }
}
