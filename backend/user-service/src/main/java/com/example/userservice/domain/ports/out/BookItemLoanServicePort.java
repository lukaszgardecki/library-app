package com.example.userservice.domain.ports.out;

import com.example.userservice.domain.integration.loan.BookItemLoan;
import com.example.userservice.domain.model.user.values.UserId;

import java.util.List;

public interface BookItemLoanServicePort {

    List<BookItemLoan> getAllLoansByUserId(UserId userId);

    List<BookItemLoan> getCurrentLoansByUserId(UserId userId);

}
