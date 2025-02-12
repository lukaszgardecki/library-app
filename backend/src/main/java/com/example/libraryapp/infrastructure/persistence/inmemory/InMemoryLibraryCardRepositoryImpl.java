package com.example.libraryapp.infrastructure.persistence.inmemory;

import com.example.libraryapp.domain.librarycard.model.LibraryCard;
import com.example.libraryapp.domain.librarycard.model.LibraryCardStatus;
import com.example.libraryapp.domain.librarycard.ports.LibraryCardRepository;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.requireNonNull;

public class InMemoryLibraryCardRepositoryImpl implements LibraryCardRepository {
    private final ConcurrentHashMap<Long, LibraryCard> map = new ConcurrentHashMap<>();
    private static long id = 0;

    @Override
    public LibraryCard save(LibraryCard cardToSave) {
        requireNonNull(cardToSave, "LibraryCard cannot be null");
        if (cardToSave.getId() == null) {
            cardToSave.setId(++id);
        }
        return map.put(cardToSave.getId(), cardToSave);
    }

    @Override
    public Optional<LibraryCard> findById(Long id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public void changeStatusByUserId(LibraryCardStatus status, Long userId) {
        map.values().stream()
                .filter(card -> card.getUserId().equals(userId))
                .findFirst()
                .ifPresent(card -> card.setStatus(status));
    }
}
