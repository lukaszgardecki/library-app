package com.example.libraryapp.domain.bookitemrequest.exceptions;

import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.bookitemrequest.model.RequestId;
import com.example.libraryapp.domain.exception.LibraryAppNotFoundException;

public class BookItemRequestNotFoundException extends LibraryAppNotFoundException {

    public BookItemRequestNotFoundException() {
        super(MessageKey.REQUEST_NOT_FOUND, "null");
    }

    public BookItemRequestNotFoundException(RequestId bookItemRequestId) {
        super(MessageKey.REQUEST_NOT_FOUND_ID, bookItemRequestId.value().toString());
    }
}
