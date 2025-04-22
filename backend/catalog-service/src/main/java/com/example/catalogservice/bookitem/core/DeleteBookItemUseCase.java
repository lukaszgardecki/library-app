package com.example.catalogservice.bookitem.core;

import com.example.catalogservice.book.domain.model.Title;
import com.example.catalogservice.bookitem.domain.exceptions.BookItemException;
import com.example.catalogservice.bookitem.domain.model.BookItem;
import com.example.catalogservice.bookitem.domain.model.BookItemId;
import com.example.catalogservice.bookitem.domain.model.BookItemStatus;
import com.example.catalogservice.bookitem.domain.ports.BookItemRepositoryPort;
import com.example.catalogservice.bookitem.domain.ports.EventPublisherPort;
import com.example.catalogservice.common.MessageKey;
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
            Title bookTitle = bookItemService.getBookTitleByBookId(bookItem.getBookId());
            publisher.publishBookItemDeletedEvent(bookItem.getId(), bookTitle);
        }
    }
}
