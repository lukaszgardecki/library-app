package com.example.userservice.user.infrastructure.integration.bookitemloanservice;

import com.example.userservice.user.domain.dto.BookItemLoanDto;
import com.example.userservice.user.domain.model.user.UserId;
import com.example.userservice.user.domain.ports.BookItemLoanServicePort;
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
