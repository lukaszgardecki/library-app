package com.example.fineservice.domain.ports;

import com.example.fineservice.domain.model.FineAmount;
import com.example.fineservice.domain.model.UserId;

public interface EventPublisherPort {

    void publishFinePaidEvent(UserId userId, FineAmount amount);
}
