package com.example.loanservice.domain.event.outgoing;

import com.example.loanservice.domain.model.values.BookItemId;
import com.example.loanservice.domain.integration.catalog.Title;
import com.example.loanservice.domain.model.values.UserId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoanProlongationNotAllowed {
    private BookItemId bookItemId;
    private UserId userId;
    private Title bookTitle;
}
