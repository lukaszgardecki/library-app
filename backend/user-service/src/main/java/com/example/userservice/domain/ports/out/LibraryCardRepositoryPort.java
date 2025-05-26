package com.example.userservice.domain.ports.out;

import com.example.userservice.domain.model.librarycard.LibraryCard;
import com.example.userservice.domain.model.librarycard.values.LibraryCardId;
import com.example.userservice.domain.model.librarycard.values.LibraryCardStatus;

import java.util.Optional;

public interface LibraryCardRepositoryPort {

    LibraryCard save(LibraryCard cardToSave);

    Optional<LibraryCard> findById(LibraryCardId id);

    void changeStatusById(LibraryCardStatus status, LibraryCardId id);
}
