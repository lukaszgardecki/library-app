package com.example.statisticsservice.infrastructure.kafka;

import com.example.statisticsservice.domain.integration.*;
import com.example.statisticsservice.domain.model.borrower.values.PersonFirstName;
import com.example.statisticsservice.domain.model.borrower.values.PersonLastName;
import com.example.statisticsservice.domain.model.borrower.values.UserId;
import com.example.statisticsservice.domain.ports.in.EventListenerPort;
import com.example.statisticsservice.infrastructure.kafka.event.incoming.BookItemReturnedEvent;
import com.example.statisticsservice.infrastructure.kafka.event.incoming.LoanCreatedEvent;
import com.example.statisticsservice.infrastructure.kafka.event.incoming.UserCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class KafkaConsumerAdapter {
    private final EventListenerPort eventListener;

    @KafkaListener(topics = "auth-service.user.created", groupId = "stats-service.user.created.consumers")
    void userCreated(UserCreatedEvent event) {
        eventListener.handleUserCreated(
                new UserId(event.getUserId()),
                new PersonFirstName(event.getFirstName()),
                new PersonLastName(event.getLastName()),
                new BirthDate(event.getBirthday()),
                new City(event.getAddressCity())
        );
    }

    @KafkaListener(topics = "loan-service.loan.created", groupId = "stats-service.loan.created.consumers")
    void loanCreated(LoanCreatedEvent event) {
        eventListener.handleLoanCreated(
                new UserId(event.getUserId()),
                new LoanCreationDate(event.getLoanCreationDate()),
                new Subject(event.getBookSubject())
        );
    }

    @KafkaListener(topics = "loan-service.book-item.returned", groupId = "stats-service.book-item.returned.consumers")
    void bookItemReturned(BookItemReturnedEvent event) {
        eventListener.handleBookItemReturned(new LoanReturnDate(event.getLoanReturnDate()));
    }
}
