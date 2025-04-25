package com.example.catalogservice.core.bookitem;

import com.example.catalogservice.domain.exception.BookItemException;
import com.example.catalogservice.domain.model.bookitem.BookItem;
import com.example.catalogservice.domain.model.bookitem.BookItemId;
import com.example.catalogservice.domain.model.bookitem.BookItemStatus;
import com.example.catalogservice.domain.ports.BookItemRepositoryPort;
import com.example.catalogservice.domain.ports.EventPublisherPort;
import com.example.catalogservice.domain.MessageKey;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class DeleteBookItemUseCase {
    private final BookItemRepositoryPort bookItemRepository;
    private final BookItemService bookItemService;
    private final EventPublisherPort publisher;

    void execute(BookItemId id) {
        BookItem bookItem = bookItemService.getBookItemById(id);
        if (bookItem.getStatus() == BookItemStatus.LOANED) {
            throw new BookItemException(MessageKey.BOOK_ITEM_DELETION_FAILED);
        } else {
            bookItemRepository.deleteById(id);
            publisher.publishBookItemDeletedEvent(bookItem.getId());
        }
    }
}
