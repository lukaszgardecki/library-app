package com.example.userservice.user.domain.ports;

import com.example.userservice.user.domain.model.fine.FineAmount;
import com.example.userservice.user.domain.model.user.UserId;

public interface EventListenerPort {

    void updateUserOnRequest(UserId userId);
    void updateUserOnRequestCancellation(UserId userId);
    void updateUserOnReturn(UserId userId);
    void updateUserOnLoss(UserId userId);
    void updateUserOnRenewal(UserId userId);
    void updateUserOnLoan(UserId userId);
    void updateUserOnFinePaid(UserId userId, FineAmount fineAmount);
}
