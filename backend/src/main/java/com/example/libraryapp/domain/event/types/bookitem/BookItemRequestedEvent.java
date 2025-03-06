package com.example.libraryapp.domain.event.types.bookitem;

import com.example.libraryapp.domain.bookitemrequest.dto.BookItemRequestDto;
import lombok.Getter;

@Getter
public class BookItemRequestedEvent extends BookItemEvent {
    private final BookItemRequestDto bookItemRequest;

    public BookItemRequestedEvent(BookItemRequestDto bookItemRequest, String bookTitle) {
        super(bookItemRequest.getBookItemId(), bookItemRequest.getUserId(), bookTitle);
        this.bookItemRequest = bookItemRequest;
    }
}


