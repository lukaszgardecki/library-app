package com.example.userservice.infrastructure.integration.bookitemrequestservice;

import com.example.userservice.domain.dto.user.BookItemRequestDto;
import com.example.userservice.domain.model.user.UserId;
import com.example.userservice.domain.ports.BookItemRequestServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
class BookItemRequestServiceAdapter implements BookItemRequestServicePort {
    private final BookItemRequestServiceFeignClient client;


    @Override
    public void cancelAllItemRequestsByUserId(UserId userId) {
        client.cancelAllItemRequestsByUserId(userId.value());
    }

    @Override
    public List<BookItemRequestDto> getUserCurrentBookItemRequests(UserId id) {
        ResponseEntity<List<BookItemRequestDto>> response = client.getUserCurrentBookItemRequests(id.value());
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }
        return null;
    }
}
