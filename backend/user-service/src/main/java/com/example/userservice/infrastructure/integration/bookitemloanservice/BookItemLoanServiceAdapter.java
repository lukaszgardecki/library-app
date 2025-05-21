package com.example.userservice.infrastructure.integration.bookitemloanservice;

import com.example.userservice.domain.integration.loan.BookItemLoan;
import com.example.userservice.domain.model.user.values.UserId;
import com.example.userservice.domain.ports.out.BookItemLoanServicePort;
import com.example.userservice.infrastructure.integration.bookitemloanservice.dto.BookItemLoanDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
class BookItemLoanServiceAdapter implements BookItemLoanServicePort {
    private final BookItemLoanServiceFeignClient client;


    @Override
    public List<BookItemLoan> getAllLoansByUserId(UserId userId) {
        ResponseEntity<List<BookItemLoanDto>> response = client.getAllLoansByUserId(userId.value());
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody().stream().map(BookItemLoanMapper::toModel).toList();
        }
        return null;
    }

    @Override
    public List<BookItemLoan> getCurrentLoansByUserId(UserId userId) {
        ResponseEntity<List<BookItemLoanDto>> response = client.getCurrentLoansByUserId(userId.value());
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody().stream().map(BookItemLoanMapper::toModel).toList();
        }
        return null;
    }
}
