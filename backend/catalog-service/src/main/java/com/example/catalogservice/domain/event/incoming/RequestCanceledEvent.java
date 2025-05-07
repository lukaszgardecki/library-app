package com.example.catalogservice.domain.event.incoming;

import com.example.catalogservice.domain.integration.UserId;
import com.example.catalogservice.domain.model.book.values.BookId;
import com.example.catalogservice.domain.model.book.values.Title;
import com.example.catalogservice.domain.model.bookitem.values.BookItemId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestCanceledEvent {
    private BookItemId bookItemId;
    private UserId userId;
    private BookId bookId;
    private Title bookTitle;
}
