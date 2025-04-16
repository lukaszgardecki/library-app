package com.example.libraryapp.infrastructure.persistence.inmemory;

import com.example.libraryapp.domain.librarycard.model.LibraryCard;
import com.example.libraryapp.domain.librarycard.model.LibraryCardId;
import com.example.libraryapp.domain.librarycard.model.LibraryCardStatus;
import com.example.libraryapp.domain.librarycard.ports.LibraryCardRepositoryPort;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.requireNonNull;

public class InMemoryLibraryCardRepositoryAdapter implements LibraryCardRepositoryPort {
    private final ConcurrentHashMap<LibraryCardId, LibraryCard> map = new ConcurrentHashMap<>();
    private static long id = 0;

    @Override
    public LibraryCard save(LibraryCard cardToSave) {
        requireNonNull(cardToSave, "LibraryCard cannot be null");
        if (cardToSave.getId() == null) {
            cardToSave.setId(new LibraryCardId(++id));
        }
        return map.put(cardToSave.getId(), cardToSave);
    }

    @Override
    public Optional<LibraryCard> findById(LibraryCardId id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public void changeStatusById(LibraryCardStatus status, LibraryCardId id) {
        map.values().stream()
                .filter(card -> card.getId().equals(id))
                .findFirst()
                .ifPresent(card -> card.setStatus(status));
    }
}
