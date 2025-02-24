package com.example.libraryapp.application.bookitemrequest;

import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.bookitem.exceptions.BookItemException;
import com.example.libraryapp.domain.bookitemrequest.model.BookItemRequestStatus;
import com.example.libraryapp.domain.bookitemrequest.ports.BookItemRequestRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
class EnsureBookItemNotRequestedUseCase {
    private final BookItemRequestRepositoryPort bookItemRequestRepository;

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
                    throw new BookItemException(MessageKey.BOOK_ITEM_ALREADY_REQUESTED);
                });
    }
}
