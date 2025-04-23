package com.example.userservice.domain.ports;

import com.example.userservice.domain.dto.user.BookItemLoanDto;
import com.example.userservice.domain.model.user.UserId;

import java.util.List;

public interface BookItemLoanServicePort {

    List<BookItemLoanDto> getAllLoansByUserId(UserId userId);

    List<BookItemLoanDto> getCurrentLoansByUserId(UserId userId);

}
