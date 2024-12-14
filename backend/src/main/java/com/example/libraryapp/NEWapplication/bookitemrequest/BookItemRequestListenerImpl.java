package com.example.libraryapp.NEWapplication.bookitemrequest;

import com.example.libraryapp.NEWapplication.book.BookFacade;
import com.example.libraryapp.NEWapplication.bookitem.BookItemFacade;
import com.example.libraryapp.NEWdomain.bookitem.dto.BookItemDto;
import com.example.libraryapp.NEWdomain.bookitemrequest.model.BookItemRequest;
import com.example.libraryapp.NEWdomain.bookitemrequest.model.BookItemRequestStatus;
import com.example.libraryapp.NEWdomain.bookitemrequest.ports.BookItemRequestListenerPort;
import com.example.libraryapp.NEWinfrastructure.events.event.CustomEvent;
import com.example.libraryapp.NEWinfrastructure.events.event.bookitem.BookItemAvailableToLoanEvent;
import com.example.libraryapp.NEWinfrastructure.events.event.bookitem.BookItemReturnedEvent;
import com.example.libraryapp.NEWinfrastructure.events.publishers.EventPublisherPort;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
class BookItemRequestListenerImpl implements BookItemRequestListenerPort {
    private final BookItemRequestService bookItemRequestService;
    private final BookItemFacade bookItemFacade;
    private final BookFacade bookFacade;
    private final EventPublisherPort publisher;


    @Override
    public List<Class<? extends CustomEvent>> getSupportedEventTypes() {
        return List.of(
                BookItemReturnedEvent.class
        );
    }

    @Override
    public void onEvent(CustomEvent event) {
        if (event instanceof BookItemReturnedEvent e) {
            handleBookItemReturnedEvent(e);
        }
    }

    private void handleBookItemReturnedEvent(BookItemReturnedEvent event) {
        System.out.println("Zwróciłem książkę id: " + event.getBookItemId());
        List<BookItemRequestStatus> statusesToFind = List.of(BookItemRequestStatus.PENDING, BookItemRequestStatus.READY);
        List<BookItemRequest> requests = bookItemRequestService.findByBookItemIdAndStatuses(event.getBookItemId(), statusesToFind);
        requests.stream()
                .min(Comparator.comparing(BookItemRequest::getCreationDate))
                .ifPresent(req -> {
                    BookItemDto bookItem = bookItemFacade.getBookItem(req.getBookItemId());
                    String bookTitle = bookFacade.getBook(bookItem.getId()).getTitle();
                    publisher.publish(new BookItemAvailableToLoanEvent(bookItem.getId(), req.getUserId(), bookTitle));
                });
    }
}
