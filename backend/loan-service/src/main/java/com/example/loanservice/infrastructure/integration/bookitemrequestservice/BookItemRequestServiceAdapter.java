package com.example.loanservice.infrastructure.integration.bookitemrequestservice;

import com.example.loanservice.domain.integration.requestservice.RequestId;
import com.example.loanservice.domain.model.values.BookItemId;
import com.example.loanservice.domain.model.values.UserId;
import com.example.loanservice.domain.ports.out.BookItemRequestServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class BookItemRequestServiceAdapter implements BookItemRequestServicePort {
    private final BookItemRequestServiceFeignClient client;

    @Override
    public RequestId checkIfBookItemRequestStatusIsReady(BookItemId bookItemId, UserId userId) {
        ResponseEntity<Long> response = client.checkIfBookItemRequestStatusIsReady(bookItemId.value(), userId.value());
        if (response.getStatusCode().is2xxSuccessful()) {
            return new RequestId(response.getBody());
        }
        return null;
    }

    @Override
    public void ensureBookItemNotRequested(BookItemId bookItemId) {
        ResponseEntity<Void> response = client.ensureBookItemNotRequested(bookItemId.value());
        if (response.getStatusCode().is2xxSuccessful()) {

        }
    }

    @Override
    public Boolean isBookItemRequested(BookItemId bookItemId) {
        ResponseEntity<Boolean> response = client.isBookItemRequested(bookItemId.value());
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }
        return null;
    }
}
