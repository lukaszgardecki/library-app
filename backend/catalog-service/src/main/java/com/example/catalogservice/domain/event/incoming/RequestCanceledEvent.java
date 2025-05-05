package com.example.catalogservice.domain.event.incoming;

import com.example.catalogservice.domain.model.UserId;
import com.example.catalogservice.domain.model.book.BookId;
import com.example.catalogservice.domain.model.bookitem.BookItemId;
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
}
