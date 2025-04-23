package com.example.userservice.domain.ports;

import com.example.userservice.domain.dto.user.BookDto;
import com.example.userservice.domain.dto.user.BookItemDto;
import com.example.userservice.domain.model.book.BookId;
import com.example.userservice.domain.model.bookitem.BookItemId;

public interface BookCatalogServicePort {

    BookItemDto getBookItem(BookItemId bookItemId);

    BookDto getBook(BookId bookId);
}
