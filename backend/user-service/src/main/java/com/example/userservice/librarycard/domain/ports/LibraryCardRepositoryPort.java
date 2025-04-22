package com.example.userservice.librarycard.domain.ports;

import com.example.userservice.librarycard.domain.model.LibraryCard;
import com.example.userservice.librarycard.domain.model.LibraryCardId;
import com.example.userservice.librarycard.domain.model.LibraryCardStatus;

import java.util.Optional;

public interface LibraryCardRepositoryPort {

    LibraryCard save(LibraryCard cardToSave);

    Optional<LibraryCard> findById(LibraryCardId id);

    void changeStatusById(LibraryCardStatus status, LibraryCardId id);
}
