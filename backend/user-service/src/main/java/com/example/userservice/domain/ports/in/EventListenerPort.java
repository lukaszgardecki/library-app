package com.example.userservice.domain.ports.in;

import com.example.userservice.domain.integration.fine.FineAmount;
import com.example.userservice.domain.model.user.values.UserId;

public interface EventListenerPort {

    void handleRequestCreatedEvent(UserId userId);
    void handleRequestCanceledEvent(UserId userId);
    void handleLoanCreatedEvent(UserId userId);
    void handleLoanProlongedEvent(UserId userId);
    void handleBookItemReturnedEvent(UserId userId);
    void handleBookItemLostEvent(UserId userId);
    void handleFinePaidEvent(UserId userId, FineAmount fineAmount);
}
