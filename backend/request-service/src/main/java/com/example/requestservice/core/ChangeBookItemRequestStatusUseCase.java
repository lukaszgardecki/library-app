package com.example.requestservice.core;

import com.example.requestservice.domain.model.BookItemRequestStatus;
import com.example.requestservice.domain.model.RequestId;
import com.example.requestservice.domain.ports.BookItemRequestRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class ChangeBookItemRequestStatusUseCase {
    private final BookItemRequestRepositoryPort bookItemRequestRepository;

    void execute(RequestId id, BookItemRequestStatus newStatus) {
        bookItemRequestRepository.setBookRequestStatus(id, newStatus);
    }
}
