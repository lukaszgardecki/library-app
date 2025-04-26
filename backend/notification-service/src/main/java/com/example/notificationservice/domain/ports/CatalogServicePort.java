package com.example.notificationservice.domain.ports;

import com.example.notificationservice.domain.dto.BookDto;
import com.example.notificationservice.domain.model.BookItemId;

public interface CatalogServicePort {

    BookDto getBookByBookItemId(BookItemId bookItemId);
}
