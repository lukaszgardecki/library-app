package com.example.catalogservice.domain.event.incoming;

import com.example.catalogservice.domain.model.UserId;
import com.example.catalogservice.domain.model.bookitem.BookItemId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RequestCanceledEvent {
    private BookItemId bookItemId;
    private UserId userId;
}
