package com.example.libraryapp.domain.notification.types.bookitem;

import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.message.ports.MessageProviderPort;
import com.example.libraryapp.domain.notification.model.NotificationType;
import com.example.libraryapp.domain.event.types.bookitem.BookItemEvent;

public class RenewalImpossibleNotification extends BookItemNotification {

    public RenewalImpossibleNotification(BookItemEvent event, MessageProviderPort msgProvider) {
        super(event.getUserId(), event.getBookItemId(), event.getBookTitle());
        this.type = NotificationType.RENEWAL_IMPOSSIBLE;
        this.subject = msgProvider.getMessage(MessageKey.NOTIFICATION_RENEWAL_IMPOSSIBLE_SUBJECT);
        this.content = msgProvider.getMessage(
                MessageKey.NOTIFICATION_RENEWAL_IMPOSSIBLE_CONTENT, event.getBookTitle(), event.getBookItemId()
        );
    }
}
