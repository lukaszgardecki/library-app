package com.example.userservice.domain.model.event.incoming;

import com.example.userservice.domain.model.bookitem.BookItemId;
import com.example.userservice.domain.dto.user.BookItemRequestDto;
import com.example.userservice.domain.model.book.Title;
import com.example.userservice.domain.model.user.UserId;
import lombok.Getter;

@Getter
public class BookItemRequestedEvent extends BookItemEvent {
    private final BookItemRequestDto bookItemRequest;

    public BookItemRequestedEvent(BookItemRequestDto bookItemRequest, Title bookTitle) {
        super(new BookItemId(bookItemRequest.getBookItemId()), new UserId(bookItemRequest.getUserId()), bookTitle);
        this.bookItemRequest = bookItemRequest;
    }
}


