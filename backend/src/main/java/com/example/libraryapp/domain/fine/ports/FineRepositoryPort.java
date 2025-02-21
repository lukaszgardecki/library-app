package com.example.libraryapp.domain.fine.ports;

import com.example.libraryapp.domain.fine.model.Fine;
import com.example.libraryapp.domain.fine.model.FineStatus;

import java.util.List;
import java.util.Optional;

public interface FineRepositoryPort {
    List<Fine> findAllByUserId(Long userId);

    Optional<Fine> findById(Long id);

    Fine save(Fine fineToSave);

    void setStatus(Long id, FineStatus status);
}
