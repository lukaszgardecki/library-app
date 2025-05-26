package com.example.catalogservice.domain.ports.out;

import com.example.catalogservice.domain.model.book.values.BookId;
import com.example.catalogservice.domain.model.bookitem.values.BookItemId;

public interface EventPublisherPort {

    void publishBookItemDeletedEvent(BookItemId bookItemId, BookId bookId);
}
