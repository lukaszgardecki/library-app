package com.example.userservice.domain.ports.out;

import com.example.userservice.domain.integration.catalog.BookItemId;
import com.example.userservice.domain.integration.catalog.book.Book;

public interface CatalogServicePort {

    Book getBookByBookItemId(BookItemId bookItemId);
}
