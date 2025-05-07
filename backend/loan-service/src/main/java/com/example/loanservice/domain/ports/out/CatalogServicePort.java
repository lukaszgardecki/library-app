package com.example.loanservice.domain.ports.out;

import com.example.loanservice.domain.integration.catalog.dto.BookDto;
import com.example.loanservice.domain.integration.catalog.dto.BookItemDto;
import com.example.loanservice.domain.integration.catalog.BookId;
import com.example.loanservice.domain.model.values.BookItemId;

public interface CatalogServicePort {

    BookItemDto getBookItemById(BookItemId bookItemId);

    BookDto getBookByBookItemId(BookItemId bookItemId);

    BookId getBookIdByBookItemId(BookItemId bookItemId);

    BookItemDto verifyAndGetBookItemForLoan(BookItemId bookItemId);
}
