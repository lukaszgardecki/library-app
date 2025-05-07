package com.example.loanservice.infrastructure.events;

import com.example.loanservice.domain.event.incoming.ReservationCreatedEvent;
import com.example.loanservice.domain.ports.in.EventListenerPort;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class EventListenerAdapter {
    private final EventListenerPort eventListener;

    private static final String RESERVATION_CREATED_TOPIC = "request-service.reservation.created";

    @KafkaListener(topics = RESERVATION_CREATED_TOPIC, groupId = "loan-service.reservation.created.consumers")
    void reservationCreated(ReservationCreatedEvent event) {
        eventListener.handleBookItemReservedEvent(event.getBookItemId());
    }
}
