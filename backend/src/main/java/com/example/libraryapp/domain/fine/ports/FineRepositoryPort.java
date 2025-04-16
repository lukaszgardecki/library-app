package com.example.libraryapp.domain.fine.ports;

import com.example.libraryapp.domain.fine.model.Fine;
import com.example.libraryapp.domain.fine.model.FineId;
import com.example.libraryapp.domain.fine.model.FineStatus;
import com.example.libraryapp.domain.user.model.UserId;

import java.util.List;
import java.util.Optional;

public interface FineRepositoryPort {
    List<Fine> findAllByUserId(UserId userId);

    Optional<Fine> findById(FineId id);

    Fine save(Fine fineToSave);

    void setStatus(FineId id, FineStatus status);
}
