package com.example.requestservice.domain.event.outgoing;

import com.example.requestservice.domain.integration.catalog.BookId;
import com.example.requestservice.domain.model.values.BookItemId;
import com.example.requestservice.domain.integration.catalog.Title;
import com.example.requestservice.domain.model.values.UserId;
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
