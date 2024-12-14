package com.example.libraryapp.NEWdomain.user.ports;

import com.example.libraryapp.NEWdomain.user.model.LibraryCard;

public interface LibraryCardRepository {

    LibraryCard save(LibraryCard cardToSave);
}
