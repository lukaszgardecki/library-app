package com.example.catalogservice.domain.ports.out;

import com.example.catalogservice.domain.model.bookitem.values.BookItemId;

public interface BookItemRequestServicePort {
    Boolean isBookItemRequested(BookItemId bookItemId);
}
