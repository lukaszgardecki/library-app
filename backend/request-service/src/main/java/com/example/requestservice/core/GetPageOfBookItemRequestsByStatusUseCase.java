package com.example.requestservice.core;

import com.example.requestservice.domain.model.BookItemRequest;
import com.example.requestservice.domain.model.BookItemRequestStatus;
import com.example.requestservice.domain.ports.BookItemRequestRepositoryPort;
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
