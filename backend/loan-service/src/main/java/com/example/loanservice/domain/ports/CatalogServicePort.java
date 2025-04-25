package com.example.loanservice.domain.ports;

import com.example.loanservice.domain.dto.BookItemDto;
import com.example.loanservice.domain.model.BookItemId;

public interface CatalogServicePort {

    BookItemDto getBookItemById(BookItemId bookItemId);

    BookItemDto verifyAndGetBookItemForLoan(BookItemId bookItemId);
}
