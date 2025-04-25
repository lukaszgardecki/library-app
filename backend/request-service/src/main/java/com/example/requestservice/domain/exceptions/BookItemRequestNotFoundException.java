package com.example.requestservice.domain.exceptions;

import com.example.requestservice.domain.MessageKey;
import com.example.requestservice.domain.model.RequestId;

public class BookItemRequestNotFoundException extends LibraryAppNotFoundException {

    public BookItemRequestNotFoundException() {
        super(MessageKey.REQUEST_NOT_FOUND, "null");
    }

    public BookItemRequestNotFoundException(RequestId bookItemRequestId) {
        super(MessageKey.REQUEST_NOT_FOUND_ID, bookItemRequestId.value().toString());
    }
}
