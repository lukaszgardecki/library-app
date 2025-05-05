package com.example.requestservice.domain.ports;

import com.example.requestservice.domain.dto.BookItemDto;
import com.example.requestservice.domain.model.BookId;
import com.example.requestservice.domain.model.BookItemId;

public interface CatalogServicePort {

    BookId getBookIdByBookItemId(BookItemId bookItemId);

    BookItemDto verifyAndGetBookItemForRequest(BookItemId bookItemId);
}
