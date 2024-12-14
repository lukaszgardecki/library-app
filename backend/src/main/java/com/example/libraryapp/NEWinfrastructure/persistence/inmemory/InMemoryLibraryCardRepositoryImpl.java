package com.example.libraryapp.NEWinfrastructure.persistence.inmemory;

import com.example.libraryapp.NEWdomain.user.model.LibraryCard;
import com.example.libraryapp.NEWdomain.user.ports.LibraryCardRepository;

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
        map.put(cardToSave.getId(), cardToSave);
        return cardToSave;
    }
}
