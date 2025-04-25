package com.example.requestservice.domain.event.incoming;

import com.example.requestservice.domain.model.BookItemId;
import com.example.requestservice.domain.model.UserId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BookItemDeletedEvent {
    private BookItemId bookItemId;
    private UserId userId;
}
