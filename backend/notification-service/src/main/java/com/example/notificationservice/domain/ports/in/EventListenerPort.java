package com.example.notificationservice.domain.ports.in;

import com.example.notificationservice.domain.event.incoming.*;

public interface EventListenerPort {

    void handleUserCreatedEvent(UserCreatedEvent event);

    void handleRequestCreatedEvent(RequestCreatedEvent event);

    void handleRequestReadyEvent(RequestReadyEvent event);

    void handleRequestCanceledEvent(RequestCanceledEvent event);

    void handleReservationCreatedEvent(ReservationCreatedEvent event);

    void handleRequestAvailableToLoanEvent(RequestAvailableToLoanEvent event);

    void handleLoanCreatedEvent(LoanCreatedEvent event);

    void handleLoanProlongedEvent(LoanProlongedEvent event);

    void handleLoanProlongationNotAllowedEvent(LoanProlongationNotAllowed event);

    void handleBookItemReturnedEvent(BookItemReturnedEvent event);

    void handleBookItemLostEvent(BookItemLostEvent event);
}
