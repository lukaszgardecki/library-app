package com.example.userservice.domain.event.incoming;

import com.example.userservice.domain.model.user.UserId;
import com.example.userservice.domain.model.bookitem.BookItemId;
import com.example.userservice.domain.model.book.Title;
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
