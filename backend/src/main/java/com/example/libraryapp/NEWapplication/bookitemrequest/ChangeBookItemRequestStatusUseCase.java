package com.example.libraryapp.NEWapplication.bookitemrequest;

import com.example.libraryapp.NEWdomain.bookitemrequest.model.BookItemRequestStatus;
import com.example.libraryapp.NEWdomain.bookitemrequest.ports.BookItemRequestRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class ChangeBookItemRequestStatusUseCase {
//    private final BookItemRequestService bookItemRequestService;
    private final BookItemRequestRepository bookItemRequestRepository;
//    private final EventPublisherPort publisher;

    void execute(Long id, BookItemRequestStatus newStatus) {
        bookItemRequestRepository.setBookRequestStatus(id, newStatus);
//        BookItemRequest bookItemRequest = bookItemRequestService.findBookItemRequest(id);
//        publisher.publish(new BookRequestReadyEvent(bookItemRequest));
    }
}
