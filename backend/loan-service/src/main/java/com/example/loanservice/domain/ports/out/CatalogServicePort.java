package com.example.loanservice.domain.ports.out;

import com.example.loanservice.domain.integration.catalogservice.book.Book;
import com.example.loanservice.domain.integration.catalogservice.book.values.BookId;
import com.example.loanservice.domain.integration.catalogservice.bookitem.BookItem;
import com.example.loanservice.domain.model.values.BookItemId;

public interface CatalogServicePort {

    BookItem getBookItemById(BookItemId bookItemId);

    Book getBookByBookItemId(BookItemId bookItemId);

    BookId getBookIdByBookItemId(BookItemId bookItemId);

    BookItem verifyAndGetBookItemForLoan(BookItemId bookItemId);
}
