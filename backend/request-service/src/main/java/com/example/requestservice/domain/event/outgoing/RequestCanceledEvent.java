package com.example.requestservice.domain.event.outgoing;

import com.example.requestservice.domain.model.BookId;
import com.example.requestservice.domain.model.BookItemId;
import com.example.requestservice.domain.model.Title;
import com.example.requestservice.domain.model.UserId;
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
