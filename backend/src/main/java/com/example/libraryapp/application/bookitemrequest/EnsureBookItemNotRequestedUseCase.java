package com.example.libraryapp.application.bookitemrequest;

import com.example.libraryapp.domain.bookitemrequest.exceptions.BookItemRequestException;
import com.example.libraryapp.domain.bookitemrequest.model.BookItemRequestStatus;
import com.example.libraryapp.domain.bookitemrequest.ports.BookItemRequestRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
class EnsureBookItemNotRequestedUseCase {
    private final BookItemRequestRepository bookItemRequestRepository;

    void execute(Long bookItemId) {
        bookItemRequestRepository.findByBookItemIdAndStatuses(
                    bookItemId, List.of(
                            BookItemRequestStatus.PENDING,
                            BookItemRequestStatus.READY,
                            BookItemRequestStatus.RESERVED
                        )
                ).stream()
                .findAny()
                .ifPresent(bookItemRequest -> {
                    throw new BookItemRequestException("Message.RESERVATION_ALREADY_CREATED.getMessage()");
                });
    }
}
