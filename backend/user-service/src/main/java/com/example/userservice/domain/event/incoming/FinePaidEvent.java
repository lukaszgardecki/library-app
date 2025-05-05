package com.example.userservice.domain.event.incoming;

import com.example.userservice.domain.model.fine.FineAmount;
import com.example.userservice.domain.model.user.UserId;
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
