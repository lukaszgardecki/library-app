package com.example.requestservice.core;

import com.example.requestservice.domain.MessageKey;
import com.example.requestservice.domain.exceptions.BookItemRequestException;
import com.example.requestservice.domain.model.BookItemId;
import com.example.requestservice.domain.model.BookItemRequestStatus;
import com.example.requestservice.domain.ports.BookItemRequestRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
class EnsureBookItemNotRequestedUseCase {
    private final BookItemRequestRepositoryPort bookItemRequestRepository;

    void execute(BookItemId bookItemId) {
        bookItemRequestRepository.findByBookItemIdAndStatuses(
                    bookItemId, List.of(
                            BookItemRequestStatus.PENDING,
                            BookItemRequestStatus.READY,
                            BookItemRequestStatus.RESERVED
                        )
                ).stream()
                .findAny()
                .ifPresent(bookItemRequest -> {
                    throw new BookItemRequestException(MessageKey.BOOK_ITEM_ALREADY_REQUESTED);
                });
    }
}
