package com.example.loanservice.domain.event.outgoing;

import com.example.loanservice.domain.model.BookItemId;
import com.example.loanservice.domain.model.UserId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LoanProlongationNotAllowed {
    private final BookItemId bookItemId;
    private final UserId userId;
}
