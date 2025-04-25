package com.example.userservice.domain.event.incoming;

import com.example.userservice.domain.model.fine.FineAmount;
import com.example.userservice.domain.model.user.UserId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FinePaidEvent {
    private UserId userId;
    private FineAmount fineAmount;
}
