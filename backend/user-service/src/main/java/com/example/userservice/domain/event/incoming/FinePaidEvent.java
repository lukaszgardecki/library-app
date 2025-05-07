package com.example.userservice.domain.event.incoming;

import com.example.userservice.domain.integration.fine.FineAmount;
import com.example.userservice.domain.model.user.values.UserId;
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
