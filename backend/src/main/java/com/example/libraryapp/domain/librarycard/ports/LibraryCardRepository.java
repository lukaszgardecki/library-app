package com.example.libraryapp.domain.librarycard.ports;

import com.example.libraryapp.domain.librarycard.model.LibraryCard;
import com.example.libraryapp.domain.librarycard.model.LibraryCardStatus;

import java.util.Optional;

public interface LibraryCardRepository {

    LibraryCard save(LibraryCard cardToSave);

    Optional<LibraryCard> findById(Long id);

    void changeStatusByUserId(LibraryCardStatus status, Long userId);
}
