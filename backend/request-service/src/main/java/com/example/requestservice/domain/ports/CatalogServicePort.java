package com.example.requestservice.domain.ports;

import com.example.requestservice.domain.dto.BookDto;
import com.example.requestservice.domain.dto.BookItemDto;
import com.example.requestservice.domain.model.BookId;
import com.example.requestservice.domain.model.BookItemId;

public interface CatalogServicePort {

    BookDto getBookByBookItemId(BookItemId bookItemId);

    BookId getBookIdByBookItemId(BookItemId bookItemId);

    BookItemDto verifyAndGetBookItemForRequest(BookItemId bookItemId);
}
