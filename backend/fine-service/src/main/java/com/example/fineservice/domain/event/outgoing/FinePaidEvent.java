package com.example.fineservice.domain.event.outgoing;

import com.example.fineservice.domain.model.FineAmount;
import com.example.fineservice.domain.model.UserId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FinePaidEvent {
    private final UserId userId;
    private final FineAmount fineAmount;
}
