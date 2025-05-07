package com.example.userservice.domain.ports.out;

import com.example.userservice.domain.integration.loan.dto.BookItemLoanDto;
import com.example.userservice.domain.model.user.values.UserId;

import java.util.List;

public interface BookItemLoanServicePort {

    List<BookItemLoanDto> getAllLoansByUserId(UserId userId);

    List<BookItemLoanDto> getCurrentLoansByUserId(UserId userId);

}
