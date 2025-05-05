package com.example.notificationservice.domain.event.incoming;

import com.example.notificationservice.domain.model.BookId;
import com.example.notificationservice.domain.model.BookItemId;
import com.example.notificationservice.domain.model.UserId;
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
