package com.example.libraryapp.NEWapplication.bookitemrequest;

import com.example.libraryapp.NEWdomain.bookitemrequest.model.BookItemRequest;
import com.example.libraryapp.NEWdomain.bookitemrequest.model.BookItemRequestStatus;
import com.example.libraryapp.NEWdomain.bookitemrequest.ports.BookItemRequestRepository;
import com.example.libraryapp.NEWinfrastructure.events.event.bookitem.BookItemRequestReadyEvent;
import com.example.libraryapp.NEWinfrastructure.events.publishers.EventPublisherPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class ChangeBookItemRequestStatusToReadyUseCase {
    private final BookItemRequestService bookItemRequestService;
    private final BookItemRequestRepository bookItemRequestRepository;
    private final EventPublisherPort publisher;

    void execute(Long id) {
        bookItemRequestRepository.setBookRequestStatus(id, BookItemRequestStatus.READY);
        BookItemRequest bookItemRequest = bookItemRequestService.findBookItemRequest(id);
        publisher.publish(new BookItemRequestReadyEvent(bookItemRequest.getBookItemId(), bookItemRequest.getUserId(), "TYTUŁ KSIĄŻKI"));
    }
}
