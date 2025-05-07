package com.example.activityservice.domain.event.incoming;

import com.example.activityservice.domain.integration.catalog.BookId;
import com.example.activityservice.domain.integration.catalog.BookItemId;
import com.example.activityservice.domain.integration.catalog.Title;
import com.example.activityservice.domain.model.values.UserId;
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
