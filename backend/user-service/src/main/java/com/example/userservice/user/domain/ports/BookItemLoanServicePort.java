package com.example.userservice.user.domain.ports;

import com.example.userservice.user.domain.dto.BookItemLoanDto;
import com.example.userservice.user.domain.model.user.UserId;

import java.util.List;

public interface BookItemLoanServicePort {

    List<BookItemLoanDto> getAllLoansByUserId(UserId userId);

    List<BookItemLoanDto> getCurrentLoansByUserId(UserId userId);

}
