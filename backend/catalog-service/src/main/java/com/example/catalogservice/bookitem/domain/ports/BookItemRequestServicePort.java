package com.example.catalogservice.bookitem.domain.ports;

import com.example.catalogservice.bookitem.domain.model.BookItemId;

public interface BookItemRequestServicePort {
    Boolean isBookItemRequested(BookItemId bookItemId);
}
