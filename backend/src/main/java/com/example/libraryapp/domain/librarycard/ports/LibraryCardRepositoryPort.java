package com.example.libraryapp.domain.librarycard.ports;

import com.example.libraryapp.domain.librarycard.model.LibraryCard;
import com.example.libraryapp.domain.librarycard.model.LibraryCardId;
import com.example.libraryapp.domain.librarycard.model.LibraryCardStatus;

import java.util.Optional;

public interface LibraryCardRepositoryPort {

    LibraryCard save(LibraryCard cardToSave);

    Optional<LibraryCard> findById(LibraryCardId id);

    void changeStatusById(LibraryCardStatus status, LibraryCardId id);
}
