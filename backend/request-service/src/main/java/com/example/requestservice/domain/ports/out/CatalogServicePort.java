package com.example.requestservice.domain.ports.out;

import com.example.requestservice.domain.integration.catalog.BookId;
import com.example.requestservice.domain.integration.catalog.dto.BookDto;
import com.example.requestservice.domain.integration.catalog.dto.BookItemDto;
import com.example.requestservice.domain.model.values.BookItemId;

public interface CatalogServicePort {

    BookItemDto getBookItemById(BookItemId bookItemId);

    BookDto getBookByBookItemId(BookItemId bookItemId);

    BookId getBookIdByBookItemId(BookItemId bookItemId);

    BookItemDto verifyAndGetBookItemForRequest(BookItemId bookItemId);

    BookDto getBookById(BookId id);
}
