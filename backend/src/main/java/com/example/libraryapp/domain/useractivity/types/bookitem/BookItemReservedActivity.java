package com.example.libraryapp.domain.useractivity.types.bookitem;

import com.example.libraryapp.domain.useractivity.model.UserActivityType;
import com.example.libraryapp.domain.event.types.bookitem.BookItemEvent;
import com.example.libraryapp.domain.event.types.bookitem.BookItemReservedEvent;

public class BookItemReservedActivity extends BookItemActivity {

    public BookItemReservedActivity(BookItemEvent event) {
        super(event.getUserId());
        int queue = ((BookItemReservedEvent) event).getQueue();
        this.type = queue == 1 ? UserActivityType.BOOK_RESERVED_FIRST : UserActivityType.BOOK_RESERVED_QUEUE;
        this.message = queue == 1
                ? """
                  Message.ACTION_BOOK_RESERVED_FIRST_PERSON.getMessage(lending.getDueDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), lending.getBookItem().getBook().getTitle());
                  """
                : "Message.ACTION_BOOK_RESERVED_QUEUE.getMessage(bookTitle, queuePosition)";
    }
}
