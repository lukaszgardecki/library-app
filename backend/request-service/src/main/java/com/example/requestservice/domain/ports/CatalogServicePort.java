package com.example.requestservice.domain.ports;

import com.example.requestservice.domain.dto.BookItemDto;
import com.example.requestservice.domain.model.BookItemId;

public interface CatalogServicePort {

    BookItemDto verifyAndGetBookItemForRequest(BookItemId bookItemId);
}
