package com.example.libraryapp.domain.event.types.fine;

import com.example.libraryapp.domain.event.types.CustomEvent;
import com.example.libraryapp.domain.fine.model.FineAmount;
import com.example.libraryapp.domain.user.model.UserId;
import lombok.Getter;

@Getter
abstract class FineEvent extends CustomEvent {
    private final FineAmount fineAmount;

    protected FineEvent(UserId userId, FineAmount fineAmount) {
        super(userId);
        this.fineAmount = fineAmount;
    }
}
