package com.example.catalogservice.domain.ports;

import com.example.catalogservice.domain.model.bookitem.BookItemId;

public interface BookItemRequestServicePort {
    Boolean isBookItemRequested(BookItemId bookItemId);
}
