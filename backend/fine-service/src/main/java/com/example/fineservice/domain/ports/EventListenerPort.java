package com.example.fineservice.domain.ports;

import com.example.fineservice.domain.dto.BookItemLoanDto;
import com.example.fineservice.domain.model.Price;

public interface EventListenerPort {

    void processFineForBookReturn(BookItemLoanDto bookItemLoan);

    void processFineForBookLost(BookItemLoanDto bookItemLoan, Price charge);
}
