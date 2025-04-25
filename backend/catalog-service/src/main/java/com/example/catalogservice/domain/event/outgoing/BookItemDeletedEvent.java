package com.example.catalogservice.domain.event.outgoing;

import com.example.catalogservice.domain.model.bookitem.BookItemId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BookItemDeletedEvent {
    private final BookItemId bookItemId;
}
