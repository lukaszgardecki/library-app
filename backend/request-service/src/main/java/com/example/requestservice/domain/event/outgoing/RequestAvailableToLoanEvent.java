package com.example.requestservice.domain.event.outgoing;

import com.example.requestservice.domain.model.BookItemId;
import com.example.requestservice.domain.model.UserId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RequestAvailableToLoanEvent {
    private final BookItemId bookItemId;
    private final UserId userId;
}
