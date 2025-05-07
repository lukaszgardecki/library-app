package com.example.userservice.infrastructure.integration.bookitemrequestservice;

import com.example.userservice.domain.integration.request.dto.BookItemRequestDto;
import com.example.userservice.domain.model.user.values.UserId;
import com.example.userservice.domain.ports.out.BookItemRequestServicePort;
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
