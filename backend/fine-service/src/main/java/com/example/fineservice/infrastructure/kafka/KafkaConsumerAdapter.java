package com.example.fineservice.infrastructure.kafka;

import com.example.fineservice.domain.integration.catalog.Price;
import com.example.fineservice.domain.integration.loan.LoanDueDate;
import com.example.fineservice.domain.integration.loan.LoanReturnDate;
import com.example.fineservice.domain.model.values.LoanId;
import com.example.fineservice.domain.model.values.UserId;
import com.example.fineservice.domain.ports.in.EventListenerPort;
import com.example.fineservice.infrastructure.kafka.event.incoming.BookItemLostEvent;
import com.example.fineservice.infrastructure.kafka.event.incoming.BookItemReturnedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class KafkaConsumerAdapter {
    private final EventListenerPort eventListener;

    private static final String BOOK_ITEM_RETURNED_TOPIC = "loan-service.book-item.returned";
    private static final String BOOK_ITEM_LOST_TOPIC = "loan-service.book-item.lost";

    @KafkaListener(topics = BOOK_ITEM_RETURNED_TOPIC, groupId = "fine-service.book-item.returned.consumers")
    public void bookItemReturned(BookItemReturnedEvent event) {
        eventListener.handleBookItemReturnedEvent(
                new LoanId(event.getLoanId()),
                new UserId(event.getUserId()),
                new LoanReturnDate(event.getLoanReturnDate()),
                new LoanDueDate(event.getLoanDueDate())
        );
    }

    @KafkaListener(topics = BOOK_ITEM_LOST_TOPIC, groupId = "fine-service.book-item.lost.consumers")
    public void bookItemLost(BookItemLostEvent event) {
        eventListener.handleBookItemLostEvent(
                new LoanId(event.getLoanId()),
                new UserId(event.getUserId()),
                new LoanReturnDate(event.getLoanReturnDate()),
                new LoanDueDate(event.getLoanDueDate()),
                new Price(event.getCharge())
        );
    }
}
