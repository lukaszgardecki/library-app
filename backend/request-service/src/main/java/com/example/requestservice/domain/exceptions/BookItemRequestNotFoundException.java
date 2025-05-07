package com.example.requestservice.domain.exceptions;

import com.example.requestservice.domain.i18n.MessageKey;
import com.example.requestservice.domain.model.values.RequestId;

public class BookItemRequestNotFoundException extends LibraryAppNotFoundException {

    public BookItemRequestNotFoundException() {
        super(MessageKey.REQUEST_NOT_FOUND, "null");
    }

    public BookItemRequestNotFoundException(RequestId bookItemRequestId) {
        super(MessageKey.REQUEST_NOT_FOUND_ID, bookItemRequestId.value().toString());
    }
}
