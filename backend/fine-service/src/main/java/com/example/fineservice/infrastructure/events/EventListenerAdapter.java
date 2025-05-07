package com.example.fineservice.infrastructure.events;

import com.example.fineservice.domain.event.incoming.BookItemLostEvent;
import com.example.fineservice.domain.event.incoming.BookItemReturnedEvent;
import com.example.fineservice.domain.ports.in.EventListenerPort;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class EventListenerAdapter {
    private final EventListenerPort eventListener;

    private static final String BOOK_ITEM_RETURNED_TOPIC = "loan-service.book-item.returned";
    private static final String BOOK_ITEM_LOST_TOPIC = "loan-service.book-item.lost";

    @KafkaListener(topics = BOOK_ITEM_RETURNED_TOPIC, groupId = "fine-service.book-item.returned.consumers")
    public void bookItemReturned(BookItemReturnedEvent event) {
        eventListener.handleBookItemReturnedEvent(
                event.getLoanId(), event.getUserId(), event.getLoanReturnDate(), event.getLoanDueDate()
        );
    }

    @KafkaListener(topics = BOOK_ITEM_LOST_TOPIC, groupId = "fine-service.book-item.lost.consumers")
    public void bookItemLost(BookItemLostEvent event) {
        eventListener.handleBookItemLostEvent(
                event.getLoanId(), event.getUserId(), event.getLoanReturnDate(), event.getLoanDueDate(), event.getCharge()
        );
    }
}
