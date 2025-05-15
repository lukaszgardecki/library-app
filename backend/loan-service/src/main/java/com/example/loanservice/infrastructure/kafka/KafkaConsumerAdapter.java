package com.example.loanservice.infrastructure.kafka;

import com.example.loanservice.domain.model.values.BookItemId;
import com.example.loanservice.domain.ports.in.EventListenerPort;
import com.example.loanservice.infrastructure.kafka.event.incoming.ReservationCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class KafkaConsumerAdapter {
    private final EventListenerPort eventListener;

    private static final String RESERVATION_CREATED_TOPIC = "request-service.reservation.created";

    @KafkaListener(topics = RESERVATION_CREATED_TOPIC, groupId = "loan-service.reservation.created.consumers")
    void reservationCreated(ReservationCreatedEvent event) {
        eventListener.handleBookItemReservedEvent(new BookItemId(event.getBookItemId()));
    }
}
