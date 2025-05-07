package com.example.fineservice.domain.event.outgoing;

import com.example.fineservice.domain.model.values.FineAmount;
import com.example.fineservice.domain.model.values.UserId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FinePaidEvent {
    private UserId userId;
    private FineAmount fineAmount;
}
