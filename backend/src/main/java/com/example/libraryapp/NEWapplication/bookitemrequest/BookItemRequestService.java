package com.example.libraryapp.NEWapplication.bookitemrequest;

import com.example.libraryapp.NEWdomain.bookitem.exceptions.BookItemNotFoundException;
import com.example.libraryapp.NEWdomain.bookitemrequest.exceptions.BookItemRequestException;
import com.example.libraryapp.NEWdomain.bookitemrequest.model.BookItemRequest;
import com.example.libraryapp.NEWdomain.bookitemrequest.model.BookItemRequestStatus;
import com.example.libraryapp.NEWdomain.bookitemrequest.ports.BookItemRequestRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
class BookItemRequestService {
    private final BookItemRequestRepository bookItemRequestRepository;

    BookItemRequest findBookItemRequest(Long id) {
        return bookItemRequestRepository.findById(id)
                .orElseThrow(() -> new BookItemNotFoundException(id));
    }

    BookItemRequest findBookItemRequest(Long bookItemId, Long userId) {
        return bookItemRequestRepository.find(bookItemId, userId)
                .orElseThrow(() -> new BookItemNotFoundException("JAKAS WIADOMOSC"));
    }

    List<BookItemRequest> findByBookItemIdAndStatuses(Long bookItemId, List<BookItemRequestStatus> statuses) {
        return bookItemRequestRepository.findByBookItemIdAndStatuses(bookItemId, statuses);
    }


    public BookItemRequest saveRequest(Long bookItemId, Long userId) {
        BookItemRequest request = createBookItemRequestToSave(bookItemId, userId);
        return bookItemRequestRepository.save(request);
    }

    public void verifyIfRequestExists(Long bookItemId, Long userId) {
        bookItemRequestRepository.find(bookItemId, userId)
                .filter(request ->
                        List.of(BookItemRequestStatus.PENDING, BookItemRequestStatus.READY).contains(request.getStatus())
                )
                .ifPresent(bookRequest -> {
                    throw new BookItemRequestException("Message.RESERVATION_ALREADY_CREATED.getMessage()");
                });
    }

    private BookItemRequest createBookItemRequestToSave(Long bookItemId, Long userId) {
        return BookItemRequest.builder()
                .creationDate(LocalDateTime.now())
                .status(BookItemRequestStatus.PENDING)
                .userId(userId)
                .bookItemId(bookItemId)
                .build();
    }
}
