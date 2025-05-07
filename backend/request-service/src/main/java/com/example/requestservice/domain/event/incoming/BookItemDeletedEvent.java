package com.example.requestservice.domain.event.incoming;

import com.example.requestservice.domain.integration.catalog.BookId;
import com.example.requestservice.domain.model.values.BookItemId;
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
