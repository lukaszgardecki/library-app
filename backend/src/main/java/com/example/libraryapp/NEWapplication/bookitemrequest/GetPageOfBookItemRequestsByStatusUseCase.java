package com.example.libraryapp.NEWapplication.bookitemrequest;

import com.example.libraryapp.NEWdomain.bookitemrequest.model.BookItemRequest;
import com.example.libraryapp.NEWdomain.bookitemrequest.model.BookItemRequestStatus;
import com.example.libraryapp.NEWdomain.bookitemrequest.ports.BookItemRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
class GetPageOfBookItemRequestsByStatusUseCase {
    private final BookItemRequestRepository bookItemRequestRepository;


    Page<BookItemRequest> execute(BookItemRequestStatus status, Pageable pageable) {
        return bookItemRequestRepository.findAllByStatus(status, pageable);
    }
}
