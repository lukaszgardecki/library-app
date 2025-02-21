package com.example.libraryapp.application.bookitemrequest;

import com.example.libraryapp.domain.bookitemrequest.model.BookItemRequest;
import com.example.libraryapp.domain.bookitemrequest.model.BookItemRequestStatus;
import com.example.libraryapp.domain.bookitemrequest.ports.BookItemRequestRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
class GetPageOfBookItemRequestsByStatusUseCase {
    private final BookItemRequestRepositoryPort bookItemRequestRepository;


    Page<BookItemRequest> execute(BookItemRequestStatus status, Pageable pageable) {
        return bookItemRequestRepository.findAllByStatus(status, pageable);
    }
}
