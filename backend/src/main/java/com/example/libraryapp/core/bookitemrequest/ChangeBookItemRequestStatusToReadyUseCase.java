package com.example.libraryapp.core.bookitemrequest;

import com.example.libraryapp.domain.book.model.Title;
import com.example.libraryapp.domain.bookitemrequest.model.BookItemRequest;
import com.example.libraryapp.domain.bookitemrequest.model.BookItemRequestStatus;
import com.example.libraryapp.domain.bookitemrequest.model.RequestId;
import com.example.libraryapp.domain.bookitemrequest.ports.BookItemRequestRepositoryPort;
import com.example.libraryapp.domain.event.types.bookitem.BookItemRequestReadyEvent;
import com.example.libraryapp.domain.event.ports.EventPublisherPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class ChangeBookItemRequestStatusToReadyUseCase {
    private final BookItemRequestService bookItemRequestService;
    private final BookItemRequestRepositoryPort bookItemRequestRepository;
    private final EventPublisherPort publisher;

    void execute(RequestId id) {
        bookItemRequestRepository.setBookRequestStatus(id, BookItemRequestStatus.READY);
        BookItemRequest bookItemRequest = bookItemRequestService.getCurrentBookItemRequestById(id);
        publisher.publish(
                new BookItemRequestReadyEvent(

                        // TODO: 01.04.2025 jak pobrać tytuł książki?
                        bookItemRequest.getBookItemId(), bookItemRequest.getUserId(), new Title("TYTUŁ KSIĄŻKI")
                )
        );
    }
}
