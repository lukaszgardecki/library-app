package com.example.userservice.core.user;

import com.example.userservice.domain.integration.fine.FineAmount;
import com.example.userservice.domain.model.user.values.UserId;
import com.example.userservice.domain.ports.in.EventListenerPort;
import com.example.userservice.domain.ports.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class EventListenerService implements EventListenerPort {
    private final UserRepositoryPort userRepository;

    @Override
    public void handleRequestCreatedEvent(UserId userId) {
        userRepository.incrementTotalBooksRequested(userId);
    }

    @Override
    public void handleRequestCanceledEvent(UserId userId) {
        userRepository.decrementTotalBooksRequested(userId);
    }

    @Override
    public void handleLoanCreatedEvent(UserId userId) {
        userRepository.decrementTotalBooksRequested(userId);
        userRepository.incrementTotalBooksBorrowed(userId);
    }

    @Override
    public void handleLoanProlongedEvent(UserId userId) {
        // nothing ?
    }

    @Override
    public void handleBookItemReturnedEvent(UserId userId) {
        userRepository.decrementTotalBooksBorrowed(userId);
    }

    @Override
    public void handleBookItemLostEvent(UserId userId) {
        userRepository.decrementTotalBooksBorrowed(userId);
    }

    @Override
    public void handleFinePaidEvent(UserId userId, FineAmount fineAmount) {
        userRepository.reduceChargeByAmount(userId, fineAmount);
    }
}
