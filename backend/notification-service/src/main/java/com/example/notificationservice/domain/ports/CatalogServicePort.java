package com.example.notificationservice.domain.ports;

import com.example.notificationservice.domain.dto.BookDto;
import com.example.notificationservice.domain.model.BookId;
import com.example.notificationservice.domain.model.BookItemId;

public interface CatalogServicePort {

    BookDto getBookById(BookId bookId);

    BookDto getBookByBookItemId(BookItemId bookItemId);
}