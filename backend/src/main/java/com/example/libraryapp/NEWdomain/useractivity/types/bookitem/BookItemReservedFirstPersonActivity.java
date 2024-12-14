package com.example.libraryapp.NEWdomain.useractivity.types.bookitem;

import com.example.libraryapp.NEWdomain.useractivity.model.UserActivity;
import com.example.libraryapp.NEWdomain.useractivity.model.UserActivityType;
import com.example.libraryapp.NEWinfrastructure.events.event.bookitem.BookItemEvent;

import java.time.LocalDateTime;

public class BookItemReservedFirstPersonActivity extends BookItemActivity {

    public BookItemReservedFirstPersonActivity(BookItemEvent event) {
        super(event.getUserId());
        this.type = UserActivityType.BOOK_RESERVED_FIRST;
        this.message = """
                Message.ACTION_BOOK_RESERVED_FIRST_PERSON.getMessage(lending.getDueDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), lending.getBookItem().getBook().getTitle());
                """;

    }

//    public BookItemReservedFirstPersonActivity(Long userId, String bookTitle, LocalDateTime loanDueDate) {
//        super(userId);
//        this.type = UserActivityType.BOOK_RESERVED_FIRST;
//        this.message = """
//                Message.ACTION_BOOK_RESERVED_FIRST_PERSON.getMessage(lending.getDueDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), lending.getBookItem().getBook().getTitle());
//                """;
//    }
}
