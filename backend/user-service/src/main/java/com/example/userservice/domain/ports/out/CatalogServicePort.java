package com.example.userservice.domain.ports.out;

import com.example.userservice.domain.integration.catalog.dto.BookDto;
import com.example.userservice.domain.integration.catalog.BookItemId;

public interface CatalogServicePort {

    BookDto getBookByBookItemId(BookItemId bookItemId);
}
