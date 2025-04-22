package com.example.libraryapp.domain.useractivity.types.bookitem;

import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.bookitem.model.IsReferenceOnly;
import com.example.libraryapp.domain.message.ports.MessageProviderPort;
import com.example.libraryapp.domain.useractivity.model.UserActivityMessage;
import com.example.libraryapp.domain.useractivity.model.UserActivityType;
import com.example.libraryapp.domain.event.types.bookitem.BookItemLoanedEvent;

public class BookItemBorrowedActivity extends BookItemActivity {
    private IsReferenceOnly isReferenceOnly;

    public BookItemBorrowedActivity(BookItemLoanedEvent event, MessageProviderPort msgProvider) {
        super(event.getUserId());
        this.type = UserActivityType.BOOK_BORROWED;
        this.isReferenceOnly = event.getIsReferenceOnly();
        if (isReferenceOnly.value()) {
            this.message = new UserActivityMessage(msgProvider.getMessage(
                    MessageKey.ACTIVITY_BOOK_ITEM_BORROWED_ON_SITE, event.getBookTitle().value()
            ));
        } else {
            this.message = new UserActivityMessage(msgProvider.getMessage(
                    MessageKey.ACTIVITY_BOOK_ITEM_BORROWED, event.getBookTitle().value()
            ));
        }
    }
}
