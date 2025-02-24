package com.example.libraryapp.application.bookitem;

import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.bookitem.exceptions.BookItemException;
import com.example.libraryapp.domain.bookitem.model.BookItem;
import com.example.libraryapp.domain.bookitem.model.BookItemStatus;
import com.example.libraryapp.domain.bookitem.ports.BookItemRepositoryPort;
import com.example.libraryapp.domain.event.ports.EventPublisherPort;
import com.example.libraryapp.domain.event.types.bookitem.BookItemDeletedEvent;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class DeleteBookItemUseCase {
    private final BookItemRepositoryPort bookItemRepository;
    private final BookItemService bookItemService;
    private final EventPublisherPort publisher;

    void execute(Long id) {
        BookItem bookItem = bookItemService.getBookItemById(id);
        if (bookItem.getStatus() == BookItemStatus.LOANED) {
            throw new BookItemException(MessageKey.BOOK_ITEM_DELETION_FAILED);
        } else {
            bookItemRepository.deleteById(id);
            String bookTitle = bookItemService.getBookTitleByBookId(bookItem.getBookId());
            publisher.publish(new BookItemDeletedEvent(bookItem.getId(), bookTitle));
        }
    }
}
