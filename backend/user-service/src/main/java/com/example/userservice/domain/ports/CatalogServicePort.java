package com.example.userservice.domain.ports;

import com.example.userservice.domain.dto.user.BookDto;
import com.example.userservice.domain.model.bookitem.BookItemId;

public interface CatalogServicePort {

    BookDto getBookByBookItemId(BookItemId bookItemId);
}
