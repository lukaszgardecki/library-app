package com.example.requestservice.core;

import com.example.requestservice.domain.model.values.BookItemRequestStatus;
import com.example.requestservice.domain.model.values.RequestId;
import com.example.requestservice.domain.ports.out.BookItemRequestRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class ChangeBookItemRequestStatusUseCase {
    private final BookItemRequestRepositoryPort bookItemRequestRepository;

    void execute(RequestId id, BookItemRequestStatus newStatus) {
        bookItemRequestRepository.setBookRequestStatus(id, newStatus);
    }
}
