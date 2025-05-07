package com.example.userservice.infrastructure.integration.bookitemloanservice;

import com.example.userservice.domain.integration.loan.dto.BookItemLoanDto;
import com.example.userservice.domain.model.user.values.UserId;
import com.example.userservice.domain.ports.out.BookItemLoanServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
class BookItemLoanServiceAdapter implements BookItemLoanServicePort {
    private final BookItemLoanServiceFeignClient client;


    @Override
    public List<BookItemLoanDto> getAllLoansByUserId(UserId userId) {
        ResponseEntity<List<BookItemLoanDto>> response = client.getAllLoansByUserId(userId.value());
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }
        return null;
    }

    @Override
    public List<BookItemLoanDto> getCurrentLoansByUserId(UserId userId) {
        ResponseEntity<List<BookItemLoanDto>> response = client.getCurrentLoansByUserId(userId.value());
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }
        return null;
    }
}
