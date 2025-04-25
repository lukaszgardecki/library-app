package com.example.userservice.domain.ports;

import com.example.userservice.domain.model.fine.FineAmount;
import com.example.userservice.domain.model.user.UserId;

public interface EventListenerPort {

    void handleRequestCreatedEvent(UserId userId);
    void handleRequestCanceledEvent(UserId userId);
    void handleLoanCreatedEvent(UserId userId);
    void handleLoanProlongedEvent(UserId userId);
    void handleBookItemReturnedEvent(UserId userId);
    void handleBookItemLostEvent(UserId userId);
    void handleFinePaidEvent(UserId userId, FineAmount fineAmount);
}
