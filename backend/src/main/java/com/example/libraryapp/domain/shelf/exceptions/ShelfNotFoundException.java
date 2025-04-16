package com.example.libraryapp.domain.shelf.exceptions;

import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.exception.LibraryAppNotFoundException;
import com.example.libraryapp.domain.shelf.model.ShelfId;

public class ShelfNotFoundException extends LibraryAppNotFoundException {

    public ShelfNotFoundException(ShelfId shelfId) {
        super(MessageKey.SHELF_NOT_FOUND_ID, shelfId.value().toString());
    }

}
