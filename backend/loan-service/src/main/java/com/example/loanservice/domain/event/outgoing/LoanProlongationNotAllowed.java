package com.example.loanservice.domain.event.outgoing;

import com.example.loanservice.domain.model.BookItemId;
import com.example.loanservice.domain.model.Title;
import com.example.loanservice.domain.model.UserId;
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
