package com.example.catalogservice.domain.ports;

import com.example.catalogservice.domain.model.book.Title;
import com.example.catalogservice.domain.model.bookitem.BookItemId;

public interface EventPublisherPort {

    void publishBookItemDeletedEvent(BookItemId bookItemId, Title title);
}
