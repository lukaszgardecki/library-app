package com.example.fineservice.domain.ports;

import com.example.fineservice.domain.model.Fine;
import com.example.fineservice.domain.model.FineId;
import com.example.fineservice.domain.model.FineStatus;
import com.example.fineservice.domain.model.UserId;

import java.util.List;
import java.util.Optional;

public interface FineRepositoryPort {
    List<Fine> findAllByUserId(UserId userId);

    Optional<Fine> findById(FineId id);

    Fine save(Fine fineToSave);

    void setStatus(FineId id, FineStatus status);
}
