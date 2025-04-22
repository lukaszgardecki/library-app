package com.example.libraryapp.core.bookitemrequest;

import com.example.libraryapp.domain.bookitemrequest.model.BookItemRequestStatus;
import com.example.libraryapp.domain.bookitemrequest.model.RequestId;
import com.example.libraryapp.domain.bookitemrequest.ports.BookItemRequestRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class ChangeBookItemRequestStatusUseCase {
    private final BookItemRequestRepositoryPort bookItemRequestRepository;

    void execute(RequestId id, BookItemRequestStatus newStatus) {
        bookItemRequestRepository.setBookRequestStatus(id, newStatus);
    }
}
