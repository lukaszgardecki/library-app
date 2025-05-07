package com.example.catalogservice.domain.event.outgoing;

import com.example.catalogservice.domain.model.book.values.BookId;
import com.example.catalogservice.domain.model.bookitem.values.BookItemId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookItemDeletedEvent {
    private BookItemId bookItemId;
    private BookId bookId;
}
