package com.example.userservice.domain.event.incoming;

import com.example.userservice.domain.integration.catalog.BookId;
import com.example.userservice.domain.integration.catalog.Title;
import com.example.userservice.domain.integration.catalog.BookItemId;
import com.example.userservice.domain.model.user.values.UserId;
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
