package com.example.activityservice.domain.event.incoming;

import com.example.activityservice.domain.integration.fine.FineAmount;
import com.example.activityservice.domain.model.values.UserId;
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
