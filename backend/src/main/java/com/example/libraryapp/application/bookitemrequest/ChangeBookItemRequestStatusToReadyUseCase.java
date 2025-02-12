package com.example.libraryapp.application.bookitemrequest;

import com.example.libraryapp.domain.bookitemrequest.model.BookItemRequest;
import com.example.libraryapp.domain.bookitemrequest.model.BookItemRequestStatus;
import com.example.libraryapp.domain.bookitemrequest.ports.BookItemRequestRepository;
import com.example.libraryapp.domain.event.types.bookitem.BookItemRequestReadyEvent;
import com.example.libraryapp.domain.event.ports.EventPublisherPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class ChangeBookItemRequestStatusToReadyUseCase {
    private final BookItemRequestService bookItemRequestService;
    private final BookItemRequestRepository bookItemRequestRepository;
    private final EventPublisherPort publisher;

    void execute(Long id) {
        bookItemRequestRepository.setBookRequestStatus(id, BookItemRequestStatus.READY);
        BookItemRequest bookItemRequest = bookItemRequestService.getCurrentBookItemRequestById(id);
        publisher.publish(new BookItemRequestReadyEvent(bookItemRequest.getBookItemId(), bookItemRequest.getUserId(), "TYTUŁ KSIĄŻKI"));
    }
}
