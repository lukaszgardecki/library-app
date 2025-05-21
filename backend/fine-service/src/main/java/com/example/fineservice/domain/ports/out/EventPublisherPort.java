package com.example.fineservice.domain.ports.out;

import com.example.fineservice.domain.model.values.FineAmount;
import com.example.fineservice.domain.model.values.UserId;

public interface EventPublisherPort {

    void publishFinePaidEvent(UserId userId, FineAmount amount);
}
