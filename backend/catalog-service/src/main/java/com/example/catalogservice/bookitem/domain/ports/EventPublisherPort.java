package com.example.catalogservice.bookitem.domain.ports;

import com.example.catalogservice.book.domain.model.Title;
import com.example.catalogservice.bookitem.domain.model.BookItemId;

public interface EventPublisherPort {

    void publishBookItemDeletedEvent(BookItemId bookItemId, Title title);
}
