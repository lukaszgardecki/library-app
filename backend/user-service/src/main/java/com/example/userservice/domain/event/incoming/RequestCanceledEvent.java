package com.example.userservice.domain.event.incoming;

import com.example.userservice.domain.model.book.BookId;
import com.example.userservice.domain.model.book.Title;
import com.example.userservice.domain.model.bookitem.BookItemId;
import com.example.userservice.domain.model.user.UserId;
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
