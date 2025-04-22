package com.example.userservice.user.domain.ports;

import com.example.userservice.user.domain.dto.BookDto;
import com.example.userservice.user.domain.dto.BookItemDto;
import com.example.userservice.user.domain.model.book.BookId;
import com.example.userservice.user.domain.model.bookitem.BookItemId;

public interface BookCatalogServicePort {

    BookItemDto getBookItem(BookItemId bookItemId);

    BookDto getBook(BookId bookId);
}
