package com.example.statisticsservice.infrastructure.events;

import com.example.statisticsservice.domain.event.incoming.BookItemReturnedEvent;
import com.example.statisticsservice.domain.event.incoming.LoanCreatedEvent;
import com.example.statisticsservice.domain.event.incoming.UserCreatedEvent;
import com.example.statisticsservice.domain.ports.in.EventListenerPort;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class EventListenerAdapter {
    private final EventListenerPort eventListener;

    @KafkaListener(topics = "auth-service.user.created", groupId = "stats-service.user.created.consumers")
    void userCreated(UserCreatedEvent event) {
        eventListener.handleUserCreated(
                event.getUserId(), event.getFirstName(), event.getLastName(), event.getBirthday(), event.getAddressCity()
        );
    }

    @KafkaListener(topics = "loan-service.loan.created", groupId = "stats-service.loan.created.consumers")
    void loanCreated(LoanCreatedEvent event) {
        eventListener.handleLoanCreated(event.getUserId(), event.getLoanCreationDate(), event.getBookSubject());
    }

    @KafkaListener(topics = "loan-service.book-item.returned", groupId = "stats-service.book-item.returned.consumers")
    void bookItemReturned(BookItemReturnedEvent event) {
        eventListener.handleBookItemReturned(event.getLoanReturnDate());
    }
}
